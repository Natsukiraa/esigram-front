package com.example.esigram.domains.usecase.message

import com.example.esigram.domains.repositories.MessageRepository

class DeleteMessageUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(messageId: String) = messageRepository.deleteMessage(messageId)
}