package com.example.esigram.domains.usecase.conversation

import com.example.esigram.domains.repositories.ConversationRepository

class ObserveConversationUseCase(private val conversationRepository: ConversationRepository) {
    operator fun invoke(conversationId: String) = conversationRepository.observeConversation(conversationId)
}