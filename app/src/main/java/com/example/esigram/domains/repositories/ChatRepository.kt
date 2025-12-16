package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.responses.ChatResponse

interface ChatRepository {
    suspend fun getChatById(id: String): ChatResponse?
}