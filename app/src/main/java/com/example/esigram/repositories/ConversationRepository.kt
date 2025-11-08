package com.example.esigram.repositories

import com.example.esigram.models.Conversation
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    suspend fun getAll(): List<String>
    suspend fun getById(id: String): Conversation?
    fun observeConversation(id: String): Flow<Conversation?>
}
