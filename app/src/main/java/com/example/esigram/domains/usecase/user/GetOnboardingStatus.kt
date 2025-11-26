package com.example.esigram.domains.usecase.user

import com.example.esigram.domains.models.responses.OnboardingStatus
import com.example.esigram.domains.repositories.UserRepository
import retrofit2.Response

class GetOnboardingStatus(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Response<OnboardingStatus> = userRepository.getOnboardingStatus()
}