package com.example.esigram.usecase.conversation

import com.example.esigram.repositories.ConversationRepository

class GetAllUseCase(private val conversationRepository: ConversationRepository) {
    suspend operator fun invoke() = conversationRepository.getAll()
}