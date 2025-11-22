package com.example.esigram.domains.usecase.story

import com.example.esigram.domains.repositories.StoryRepository
import java.io.File

class CreateStoryUseCase(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(file: File) = storyRepository.createStory(file)
}
