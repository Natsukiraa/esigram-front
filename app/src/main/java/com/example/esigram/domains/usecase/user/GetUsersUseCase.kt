package com.example.esigram.domains.usecase.user

import com.example.esigram.domains.repositories.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(page: Int, size: Int, username: String?) =
        userRepository.getUsers(page, size, username)
}