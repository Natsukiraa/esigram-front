package com.example.esigram.usecase.auth

import com.example.esigram.repositories.AuthRepository

class GetUserIdTokenUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(result: (String?) -> Unit) = authRepository.getUserIdToken(result)
}