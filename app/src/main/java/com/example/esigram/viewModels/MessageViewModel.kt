package com.example.esigram.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.datas.remote.MessageRemoteDataSource
import com.example.esigram.domains.models.Message
import com.example.esigram.domains.models.User
import com.example.esigram.domains.models.responses.ChatResponse
import com.example.esigram.domains.usecase.chat.ChatUseCases
import com.example.esigram.domains.usecase.message.MessageUseCases
import com.example.esigram.domains.usecase.user.UserUseCases
import com.example.esigram.viewModels.utils.uriToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class MessageViewModel(
    private val messageUseCases: MessageUseCases,
    private val userUseCases: UserUseCases,
    private val chatUseCases: ChatUseCases
) : ViewModel() {

    private val _allMessages = MutableStateFlow<List<Message>>(emptyList())
    val allMessages = _allMessages.asStateFlow()

    private val _allAuthors = MutableStateFlow<Set<User>>(emptySet())
    val allAuthors = _allAuthors.asStateFlow()

    private val _chat = MutableStateFlow<ChatResponse?>(null)
    val chat = _chat.asStateFlow()



    private var isLoadingMore = false

    fun startListening(chatId: String, userId: String) {
        viewModelScope.launch {
            val initialMessages = messageUseCases.loadOlderMessageUseCase(
                chatId,
                userId,
                System.currentTimeMillis(),
                ""
            )

            _allMessages.value = initialMessages.reversed()

            val lastMessage = _allMessages.value.lastOrNull()
            val lastDate = lastMessage?.createdAt?.toString()

            messageUseCases.listenMessagesUseCase(chatId, userId, lastDate).collect { event ->
                when (event) {
                    is MessageRemoteDataSource.RealtimeMessageEvent.OnNewMessage -> {
                        val currentList = _allMessages.value
                        if (currentList.none { it.id == event.message.id }) {
                            _allMessages.value = currentList + event.message
                        }
                    }

                    is MessageRemoteDataSource.RealtimeMessageEvent.OnRemoveMessage -> {
                        _allMessages.value = _allMessages.value.filter { it.id != event.messageId }
                    }
                }
            }
        }
    }

    fun onLoadMore(chatId: String, currentUserId: String) {
        if (isLoadingMore) return

        val currentList = _allMessages.value
        val currentOldestMessage = currentList.firstOrNull() ?: return

        isLoadingMore = true
        Log.d("MessageViewModel", "Loading more... oldest is ${currentOldestMessage.createdAt}")

        viewModelScope.launch {
            try {
                val newBatch = messageUseCases.loadOlderMessageUseCase(
                    chatId,
                    currentUserId,
                    currentOldestMessage.createdAt.toEpochMilli(),
                    currentOldestMessage.id
                )

                if (newBatch.isNotEmpty()) {
                    val uniqueBatch = newBatch.filter { newMsg ->
                        currentList.none { existingMsg -> existingMsg.id == newMsg.id }
                    }

                    if (uniqueBatch.isNotEmpty()) {
                        _allMessages.value = uniqueBatch.reversed() + currentList
                        Log.d(
                            "MessageViewModel",
                            "Added ${uniqueBatch.size} messages. New Total: ${_allMessages.value.size}"
                        )
                    } else {
                        Log.d("MessageViewModel", "Batch was empty after filtering duplicates.")
                    }
                }

                Log.d("MessageViewModel", "All messages size : ${_allMessages.value.size}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingMore = false
            }
        }
    }

    fun createMessage(
        context: Context,
        chatId: String,
        content: String,
        files: List<Uri>? = null,
        file: File? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val filesFromUri = mutableListOf<File>()
            if (files != null) {
                filesFromUri.addAll(files.mapNotNull { uriToFile(context, it) })
            }
            if (file != null) {
                filesFromUri.add(file)
            }


            Log.d("MessageViewModel", "Files: $filesFromUri")
            messageUseCases.createMessageUseCase(
                chatId = chatId,
                content = content,
                files = filesFromUri
            )
        }
    }

    fun deleteMessage(chatId: String, messageId: String) {
        viewModelScope.launch {
            messageUseCases.deleteMessageUseCase(chatId, messageId)
            _allMessages.value = _allMessages.value.filter { it.id != messageId }
        }
    }

    fun loadUserInformations(userId: String) {
        viewModelScope.launch {
            if (_allAuthors.value.any { it.id == userId }) return@launch
            Log.d("MessageViewModel", "Loading user $userId")
            val author = userUseCases.getUserByIdCase(userId)
            if (author != null) {
                _allAuthors.value = (_allAuthors.value + author)
            }
        }
    }

    fun getChatInfo(chatId: String){
        viewModelScope.launch {
            _chat.value = chatUseCases.getChatUseCase(chatId)
            Log.d("MessageViewModel", "Chat: ${_chat.value}")
        }
    }
}