package com.example.esigram.domains.usecase.auth

import com.example.esigram.domains.repositories.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.signOut()
}