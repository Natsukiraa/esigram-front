package com.example.esigram.viewModels

import androidx.compose.runtime.mutableStateListOf
import com.example.esigram.mappers.MessageMapper
import com.example.esigram.models.Message
import com.example.esigram.repositories.MessageRepository
import java.io.File

class MessageViewModel {
    private val repo = MessageRepository()
    private val _messages = mutableStateListOf<Message>()

    @Suppress("UNCHECKED_CAST") suspend
    fun getMessages(chatId: String): List<Message> {
        val res = repo.getAll(chatId)
        val messages = mutableListOf<Message>()
        res.forEach { (key, message) ->
            messages.add(
                MessageMapper.fromMap(
                    id = key,
                    data = message as Map<String, Any>
                )
            )
        }
        return messages
    }

    suspend fun createMessage(chatId: String, content: String, files: List<File>? = null) {
        repo.createMessage(chatId = chatId, content = content, files = files)
    }

    suspend fun deleteMessage(messageId: String) {
        repo.deleteMessage(messageId = messageId)
    }
}