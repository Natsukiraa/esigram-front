package com.example.esigram.usecase.user

import com.example.esigram.repositories.UserRepository

class GetMeCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getMe()
}
