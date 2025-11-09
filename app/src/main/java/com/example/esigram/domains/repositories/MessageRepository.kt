package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.Message
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

interface MessageRepository {
    fun listenMessages(chatId: String): Flow<List<Message>>
    suspend fun createMessage(chatId: String, content: String, files: List<File>? = null): Response<Unit>
    suspend fun deleteMessage(messageId: String): Response<Unit>
}
