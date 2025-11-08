package com.example.esigram.usecase.message

import com.example.esigram.repositories.MessageRepository

class ListenMessagesUseCase(private val messageRepository: MessageRepository) {
    operator fun invoke(chatId: String) = messageRepository.listenMessages(chatId)
}