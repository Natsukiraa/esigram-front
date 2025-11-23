package com.example.esigram.datas.remote.services

import com.example.esigram.domains.models.TmpUser
import com.example.esigram.domains.models.responses.PageModel
import com.example.esigram.domains.models.responses.UserResponse
import com.example.esigram.datas.remote.models.ResponsUserDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Path

interface UserApiService {
    @GET("/users/me")
    suspend fun getMe(): Response<UserResponse>

    @GET("/users/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<ResponsUserDto>

    @Multipart
    @PATCH("/users/me")
    suspend fun patchUser(
        @Part("data") data: RequestBody,
        @Part profilePicture: MultipartBody.Part?
    ): Response<Unit>


    @GET("/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("username") username: String? = null
    ): PageModel<TmpUser>
}