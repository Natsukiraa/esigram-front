package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.MessageRemoteDataSource
import com.example.esigram.domains.repositories.MessageRepository
import java.io.File

class MessageRepositoryImpl(
    val remote: MessageRemoteDataSource = MessageRemoteDataSource()
): MessageRepository {
    override fun listenMessages(chatId: String) = remote.listenMessage(chatId)

    override suspend fun createMessage(
        chatId: String,
        content: String,
        files: List<File>?
    ) = remote.createMessage(chatId, content, files)

    override suspend fun deleteMessage(messageId: String) = remote.deleteMessage(messageId)

}