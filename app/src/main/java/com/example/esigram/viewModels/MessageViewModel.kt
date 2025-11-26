package com.example.esigram.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.models.Message
import com.example.esigram.domains.usecase.message.MessageUseCases
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class MessageViewModel(private val messageUseCases: MessageUseCases) : ViewModel() {
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> get() = _messages

    fun startListening(chatId: String) {
        viewModelScope.launch {
            messageUseCases.listenMessagesUseCase(chatId).collectLatest { newMessages ->
                _messages.clear()
                _messages.addAll(
                    elements = newMessages
                )
            }
        }
    }

    fun createMessage(chatId: String, content: String, files: List<File>? = null) {
        viewModelScope.launch {
            messageUseCases.createMessageUseCase(chatId = chatId, content = content, files = files)
        }
    }

    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            messageUseCases.deleteMessageUseCase(messageId)
        }
    }
}