package com.example.esigram.domains.usecase.conversation

import com.example.esigram.domains.repositories.ConversationRepository
import com.example.esigram.models.User

class CreatePrivateConversationUseCase(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke(userId: String, otherId: String): String? {
        val ids = listOf(userId, otherId)
        return conversationRepository.createConversation(ids, null)
    }
}