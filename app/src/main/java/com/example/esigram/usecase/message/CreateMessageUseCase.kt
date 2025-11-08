package com.example.esigram.usecase.message

import com.example.esigram.repositories.MessageRepository
import java.io.File

class CreateMessageUseCase(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(chatId: String,
                                content: String,
                                files: List<File>?) {
        messageRepository.createMessage(chatId, content, files)
    }
}