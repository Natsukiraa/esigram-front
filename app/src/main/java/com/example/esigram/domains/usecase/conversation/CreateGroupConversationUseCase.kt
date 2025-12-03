package com.example.esigram.domains.usecase.conversation

import com.example.esigram.domains.repositories.ConversationRepository

class CreateGroupConversationUseCase(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke(ids: List<String>) =
        conversationRepository.createConversation(ids)
}