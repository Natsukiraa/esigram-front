package com.example.esigram.domains.usecase.auth

import com.example.esigram.domains.repositories.AuthRepository

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.getCurrentUser()
}