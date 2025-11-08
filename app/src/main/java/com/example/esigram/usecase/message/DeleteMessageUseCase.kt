package com.example.esigram.usecase.message

import com.example.esigram.repositories.MessageRepository

class DeleteMessageUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(messageId: String) = messageRepository.deleteMessage(messageId)
}