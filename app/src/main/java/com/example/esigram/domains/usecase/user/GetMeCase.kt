package com.example.esigram.domains.usecase.user

import com.example.esigram.domains.models.UserResponse
import com.example.esigram.domains.repositories.UserRepository
import retrofit2.Response

class GetMeCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Response<UserResponse> = userRepository.getMe()
}
