package com.example.esigram.usecase.message

data class MessageUseCases(
    val listenMessagesUseCase: ListenMessagesUseCase,
    val createMessageUseCase: CreateMessageUseCase,
    val deleteMessageUseCase: DeleteMessageUseCase
)