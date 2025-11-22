package com.example.esigram.domains.usecase.user

import com.example.esigram.domains.repositories.UserRepository
import java.io.File

class PatchUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, description: String?, file: File? = null) =
        userRepository.patchUser(username, description, file)
}