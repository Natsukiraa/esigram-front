package com.example.esigram.usecase.user

import com.example.esigram.repositories.UserRepository
import java.io.File

class RegisterUserToDBUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, description: String?, file: File?= null) =
        userRepository.registerUserToDB(username, description, file)
}