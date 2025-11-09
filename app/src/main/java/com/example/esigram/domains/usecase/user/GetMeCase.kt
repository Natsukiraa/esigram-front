package com.example.esigram.domains.usecase.user

import com.example.esigram.domains.repositories.UserRepository

class GetMeCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getMe()
}
