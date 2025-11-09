package com.example.esigram.domains.usecase.user

import com.example.esigram.domains.repositories.UserRepository
import java.io.File

class RegisterUserToDBUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, description: String?, file: File?= null) =
        userRepository.registerUserToDB(username, description, file)
}