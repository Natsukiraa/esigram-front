package com.example.esigram.domains.usecase.story

import com.example.esigram.domains.repositories.StoryRepository

class GetStoriesUseCase(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(userId: String) = storyRepository.getStories(userId)
}