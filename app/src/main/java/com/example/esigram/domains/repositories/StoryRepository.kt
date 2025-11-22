package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.Story
import com.example.esigram.domains.models.responses.PageModel
import java.io.File

interface StoryRepository {
    suspend fun getStories(userId: String): PageModel<Story>
    suspend fun createStory(file: File)
}