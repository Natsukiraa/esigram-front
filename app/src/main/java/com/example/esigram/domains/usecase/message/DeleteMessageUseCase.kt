package com.example.esigram.domains.usecase.message

import com.example.esigram.domains.repositories.MessageRepository

class DeleteMessageUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(chatId: String, messageId: String) =
        messageRepository.deleteMessage(chatId, messageId)
}