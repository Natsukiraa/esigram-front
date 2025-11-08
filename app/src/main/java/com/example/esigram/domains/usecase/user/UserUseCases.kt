package com.example.esigram.domains.usecase.user

data class UserUseCases(
    val registerUserToDBUseCase: RegisterUserToDBUseCase,
    val getMeCase: GetMeCase
)