package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.ChatRemoteDataSource
import com.example.esigram.domains.repositories.ChatRepository

class ChatRepositoryImpl(
    val remote: ChatRemoteDataSource = ChatRemoteDataSource()
): ChatRepository {
    override suspend fun getChatById(id: String) = remote.getChatById(id)

}