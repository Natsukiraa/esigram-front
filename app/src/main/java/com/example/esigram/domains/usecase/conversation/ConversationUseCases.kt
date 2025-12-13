package com.example.esigram.domains.usecase.conversation

data class ConversationUseCases(
    val getAllUseCase: GetAllUseCase,
    val getByIdUseCase: GetByIdUseCase,
    val observeConversationUseCase: ObserveConversationUseCase,
    val createGroupConversationUseCase: CreateGroupConversationUseCase,
    val createPrivateConversationUseCase: CreatePrivateConversationUseCase
)