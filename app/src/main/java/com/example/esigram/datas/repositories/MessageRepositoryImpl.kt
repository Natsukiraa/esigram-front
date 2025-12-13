package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.MessageRemoteDataSource
import com.example.esigram.domains.repositories.MessageRepository
import java.io.File

class MessageRepositoryImpl(
    val remote: MessageRemoteDataSource = MessageRemoteDataSource()
) : MessageRepository {
    override fun listenMessages(chatId: String, currentUserId: String, lastDate: String?) =
        remote.listenToMessages(chatId, currentUserId, lastDate)

    override suspend fun createMessage(
        chatId: String,
        content: String,
        files: List<File>?
    ) = remote.createMessage(chatId, content, files)

    override suspend fun deleteMessage(chatId: String, messageId: String) =
        remote.deleteMessage(chatId, messageId)

    override suspend fun loadOlderMessages(
        chatId: String,
        currentUserId: String,
        beforeTimestamp: Long,
        lastMessageId: String
    ) = remote.loadOlderMessages(chatId, currentUserId, beforeTimestamp, lastMessageId)


}