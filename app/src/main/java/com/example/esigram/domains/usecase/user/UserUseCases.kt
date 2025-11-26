package com.example.esigram.domains.usecase.user

data class UserUseCases(
    val patchUserUseCase: PatchUserUseCase,
    val getMeCase: GetMeCase,
    val getUsersUseCase: GetUsersUseCase,
    val getOnboardingStatus: GetOnboardingStatus,
    val completeOnboarding: CompleteOnboarding
)