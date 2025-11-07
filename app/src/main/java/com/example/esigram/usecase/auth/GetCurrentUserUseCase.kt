package com.example.esigram.usecase.auth

import com.example.esigram.repositories.AuthRepository

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.getCurrentUser()
}