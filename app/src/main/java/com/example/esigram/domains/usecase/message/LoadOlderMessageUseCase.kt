package com.example.esigram.domains.usecase.message

import com.example.esigram.domains.repositories.MessageRepository

class LoadOlderMessageUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(
        chatId: String,
        currentUserId: String,
        beforeTimestamp: Long,
        lastMessageId: String
    ) =
        messageRepository.loadOlderMessages(chatId, currentUserId, beforeTimestamp, lastMessageId)

}