package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.ConversationRemoteDataSource
import com.example.esigram.domains.repositories.ConversationRepository

class ConversationRepositoryImpl(
    val remote: ConversationRemoteDataSource = ConversationRemoteDataSource()
) : ConversationRepository {
    override suspend fun getAll() = remote.getAll()
    override suspend fun getById(id: String) = remote.getById(id)
    override fun observeConversation(id: String) = remote.observeConversation(id)
}