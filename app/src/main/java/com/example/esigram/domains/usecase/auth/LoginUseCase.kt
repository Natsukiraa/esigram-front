package com.example.esigram.domains.usecase.auth

import com.example.esigram.domains.repositories.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, pass: String) =
        authRepository.login(email, pass)
}