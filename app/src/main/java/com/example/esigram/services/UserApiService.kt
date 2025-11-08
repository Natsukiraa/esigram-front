package com.example.esigram.services

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface UserApiService {
    @GET("/me")
    suspend fun getMe(): Response<Unit>

    @Multipart
    @PATCH("/users/me")
    suspend fun registerUserToDB(
        @Part("data") data: RequestBody,
        @Part profilePicture: okhttp3.MultipartBody.Part?
    ): Response<Unit>
}