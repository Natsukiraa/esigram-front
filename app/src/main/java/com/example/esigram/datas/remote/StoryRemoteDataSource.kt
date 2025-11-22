package com.example.esigram.datas.remote

import com.example.esigram.datas.remote.services.StoryApiService
import com.example.esigram.domains.models.CapturedMedia
import com.example.esigram.networks.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class StoryRemoteDataSource {
    private val api = RetrofitInstance.api
    private val storyService = api.create(StoryApiService::class.java)

    suspend fun getStoriesByUser(userId: String) = storyService.getStories(userId)
    suspend fun createStory(file: File) {
        val profilePicture = file.let{
            val fileReq = file.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("media", file.name, fileReq)
        }
        storyService.createStory(profilePicture)
    }
}