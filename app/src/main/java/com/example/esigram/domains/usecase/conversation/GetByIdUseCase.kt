package com.example.esigram.domains.usecase.conversation

import com.example.esigram.domains.repositories.ConversationRepository

class GetByIdUseCase(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke(id: String) = conversationRepository.getById(id)
}