package com.example.esigram.datas.remote.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MessageApiService {
    @Multipart
    @POST("chats/{chatId}/messages")
    suspend fun sendMessage(
        @Path("chatId") chatId: String,
        @Part("data") data: RequestBody,
        @Part attachments: List<MultipartBody.Part>? = null
    ): Response<Unit>

    @DELETE("chats/{chatId}/messages/{id}")
    suspend fun deleteMessage(
        @Path("chatId") chatId: String,
        @Path("id") id: String
    ): Response<Unit>
}