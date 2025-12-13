package com.example.esigram.domains.repositories

import com.example.esigram.datas.remote.MessageRemoteDataSource
import com.example.esigram.domains.models.Message
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MessageRepository {
    fun listenMessages(
        chatId: String,
        currentUserId: String,
        lastDate: String?
    ): Flow<MessageRemoteDataSource.RealtimeMessageEvent>

    suspend fun createMessage(
        chatId: String,
        content: String,
        files: List<File>? = null
    ): Result<Unit>

    suspend fun deleteMessage(chatId: String, messageId: String): Result<Unit>

    suspend fun loadOlderMessages(
        chatId: String,
        currentUserId: String,
        beforeTimestamp: Long,
        lastMessageId: String
    ): List<Message>
}
