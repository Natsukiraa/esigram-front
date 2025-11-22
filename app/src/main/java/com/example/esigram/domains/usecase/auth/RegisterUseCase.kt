package com.example.esigram.domains.usecase.auth

class RegisterUseCase(private val authRepository: com.example.esigram.domains.repositories.AuthRepository) {
    suspend operator fun invoke(email: String, pass: String) =
        authRepository.register(email, pass)
}