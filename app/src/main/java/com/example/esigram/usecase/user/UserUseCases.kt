package com.example.esigram.usecase.user

data class UserUseCases(
    val registerUserToDBUseCase: RegisterUserToDBUseCase,
    val getMeCase: GetMeCase
)