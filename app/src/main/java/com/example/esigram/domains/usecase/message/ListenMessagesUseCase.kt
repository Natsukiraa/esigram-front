package com.example.esigram.domains.usecase.message

import com.example.esigram.domains.repositories.MessageRepository

class ListenMessagesUseCase(private val messageRepository: MessageRepository) {
    operator fun invoke(chatId: String, currentUserId: String, lastDate: String?) =
        messageRepository.listenMessages(chatId, currentUserId, lastDate)
}