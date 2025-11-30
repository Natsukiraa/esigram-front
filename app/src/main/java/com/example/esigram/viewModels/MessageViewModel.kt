package com.example.esigram.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.datas.remote.MessageRemoteDataSource
import com.example.esigram.domains.models.Message
import com.example.esigram.domains.usecase.message.MessageUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class MessageViewModel(private val messageUseCases: MessageUseCases) : ViewModel() {

    private val _allMessages = MutableStateFlow<List<Message>>(emptyList())
    val allMessages = _allMessages.asStateFlow()

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

    fun createMessage(chatId: String, content: String, files: List<File>? = null) {
        viewModelScope.launch {
            messageUseCases.createMessageUseCase(chatId = chatId, content = content, files = files)
        }
    }

    fun deleteMessage(chatId: String, messageId: String) {
        viewModelScope.launch {
            messageUseCases.deleteMessageUseCase(chatId, messageId)
        }
    }
}