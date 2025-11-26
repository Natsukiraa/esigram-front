package com.example.esigram.datas.remote.services

import com.example.esigram.datas.remote.models.MediaResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MediaApiService {

    @GET("/media/{id}")
    suspend fun getMediaById(
        @Path("id") id: String
    ): Response<MediaResponseDto>
}