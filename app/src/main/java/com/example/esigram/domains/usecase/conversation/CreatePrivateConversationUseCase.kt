package com.example.esigram.domains.usecase.conversation

import com.example.esigram.domains.repositories.ConversationRepository
import com.example.esigram.models.User

class CreatePrivateConversationUseCase(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke(id: String) {
        val ids = listOf(id)
        conversationRepository.createConversation(ids)
    }
}