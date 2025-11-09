package com.example.esigram.domains.usecase.message

import com.example.esigram.domains.repositories.MessageRepository

class ListenMessagesUseCase(private val messageRepository: MessageRepository) {
    operator fun invoke(chatId: String) = messageRepository.listenMessages(chatId)
}