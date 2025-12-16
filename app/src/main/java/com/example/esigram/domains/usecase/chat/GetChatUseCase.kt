package com.example.esigram.domains.usecase.chat

import com.example.esigram.domains.repositories.ChatRepository

class GetChatUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(id: String) = chatRepository.getChatById(id)
}