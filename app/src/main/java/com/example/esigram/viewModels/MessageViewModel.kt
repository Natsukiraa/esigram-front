package com.example.esigram.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.mappers.MessageMapper
import com.example.esigram.models.Message
import com.example.esigram.repositories.MessageRepository
import kotlinx.coroutines.launch
import java.io.File

class MessageViewModel : ViewModel() {

    private val repo = MessageRepository()
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> get() = _messages

    fun getMessages(chatId: String) {
        viewModelScope.launch {
            val res = repo.getAll(chatId)
            _messages.clear()
            res.forEach { (key, message) ->
                _messages.add(
                    MessageMapper.fromMap(
                        id = key,
                        data = message as Map<String, Any>
                    )
                )
            }
        }
    }

    fun createMessage(chatId: String, content: String, files: List<File>? = null) {
        viewModelScope.launch {
            repo.createMessage(chatId = chatId, content = content, files = files)
        }
    }

    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            repo.deleteMessage(messageId)
        }
    }
}
