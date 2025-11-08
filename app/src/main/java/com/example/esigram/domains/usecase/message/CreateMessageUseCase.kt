package com.example.esigram.domains.usecase.message

import com.example.esigram.domains.repositories.MessageRepository
import java.io.File

class CreateMessageUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(chatId: String,
                                content: String,
                                files: List<File>?) {
        messageRepository.createMessage(chatId, content, files)
    }
}