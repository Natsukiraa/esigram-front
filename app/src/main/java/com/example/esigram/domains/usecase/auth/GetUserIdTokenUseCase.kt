package com.example.esigram.domains.usecase.auth

import com.example.esigram.domains.repositories.AuthRepository

class GetUserIdTokenUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(result: (String?) -> Unit) = authRepository.getUserIdToken(result)
}