package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.StoryRemoteDataSource
import com.example.esigram.domains.models.Story
import com.example.esigram.domains.models.responses.PageModel
import com.example.esigram.domains.repositories.StoryRepository
import java.io.File

class StoryRepositoryImpl (
    val remote: StoryRemoteDataSource = StoryRemoteDataSource()
): StoryRepository {
    override suspend fun getStories(userId: String): PageModel<Story> = remote.getStoriesByUser(userId)
    override suspend fun createStory(file: File) = remote.createStory(file)
}