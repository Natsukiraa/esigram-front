package com.example.esigram.usecase.conversation

import com.example.esigram.repositories.ConversationRepository

class GetByIdUseCase(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke(id: String) = conversationRepository.getById(id)
}