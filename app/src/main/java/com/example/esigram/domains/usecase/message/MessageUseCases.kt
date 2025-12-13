package com.example.esigram.domains.usecase.message

data class MessageUseCases(
    val listenMessagesUseCase: ListenMessagesUseCase,
    val createMessageUseCase: CreateMessageUseCase,
    val deleteMessageUseCase: DeleteMessageUseCase,
    val loadOlderMessageUseCase: LoadOlderMessageUseCase
)