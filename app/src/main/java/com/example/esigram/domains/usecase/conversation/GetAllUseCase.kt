package com.example.esigram.domains.usecase.conversation

import com.example.esigram.domains.repositories.ConversationRepository

class GetAllUseCase(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke() = conversationRepository.getAll()
}