package com.example.esigram.datas.repositories

import android.util.Log
import com.example.esigram.datas.mappers.toDomain
import com.example.esigram.datas.remote.ConversationRemoteDataSource
import com.example.esigram.domains.models.Conversation
import com.example.esigram.domains.repositories.ConversationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConversationRepositoryImpl(
    val remote: ConversationRemoteDataSource = ConversationRemoteDataSource()
): ConversationRepository {
    override suspend fun getAll(userId: String) = remote.getAll(userId)
    override suspend fun getById(id: String): Conversation? {
        val conversationData = remote.getById(id)
        val conversation = conversationData?.toDomain()
        return conversation
    }
    override fun observeConversation(id: String): Flow<Conversation?> {
        return remote.observeConversation(id).map { dto ->
            if (dto == null) return@map null
            val conv = dto.toDomain()
            Log.e("DEBUG_CONV", "Conversation reçue = $conv")
            conv
        }
    }
}