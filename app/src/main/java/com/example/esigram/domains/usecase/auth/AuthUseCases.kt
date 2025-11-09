package com.example.esigram.domains.usecase.auth

data class AuthUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val signOutUseCase: SignOutUseCase,
    val getUserIdTokenUseCase: GetUserIdTokenUseCase
)