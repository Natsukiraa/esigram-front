package com.example.esigram.usecase.auth

import com.example.esigram.repositories.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.signOut()
}