package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.Conversation
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    suspend fun getAll(): List<String>
    suspend fun getById(id: String): Conversation?
    fun observeConversation(id: String): Flow<Conversation?>
}
