package com.example.esigram.usecase.auth

data class AuthUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val signOutUseCase: SignOutUseCase,
    val getUserIdTokenUseCase: GetUserIdTokenUseCase
)