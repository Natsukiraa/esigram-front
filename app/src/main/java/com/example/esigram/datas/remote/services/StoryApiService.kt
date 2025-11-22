package com.example.esigram.datas.remote.services

import com.example.esigram.domains.models.Story
import com.example.esigram.domains.models.responses.PageModel
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface StoryApiService {
    @GET("/stories/{userId}")
    suspend fun getStories(@Path("userId") userId: String): PageModel<Story>

    @POST("/stories")
    suspend fun createStory(
        @Part media: MultipartBody.Part
    ): Story
}