package com.example.esigram.usecase.conversation

import com.example.esigram.repositories.ConversationRepository

class ObserveConversationUseCase(private val conversationRepository: ConversationRepository) {
    operator fun invoke(conversationId: String) = conversationRepository.observeConversation(conversationId)
}