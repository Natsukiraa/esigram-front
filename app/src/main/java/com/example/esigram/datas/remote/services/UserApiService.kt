package com.example.esigram.datas.remote.services

import com.example.esigram.domains.models.responses.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part

interface UserApiService {
    @GET("/users/me")
    suspend fun getMe(): Response<UserResponse>

    @Multipart
    @PATCH("/users/me")
    suspend fun patchUser(
        @Part("data") data: RequestBody,
        @Part profilePicture: MultipartBody.Part?
    ): Response<Unit>
}