package com.example.esigram.domains.usecase.user

import com.example.esigram.domains.repositories.UserRepository

class GetUserByIdCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: String) = userRepository.getUserByIdReal(id)
}