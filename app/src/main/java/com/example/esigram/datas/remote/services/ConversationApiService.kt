package com.example.esigram.datas.remote.services

import com.example.esigram.datas.remote.models.ConversationIdResponse
import com.example.esigram.datas.remote.models.CreateConversationRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ConversationApiService {

    @Multipart
    @POST("chats")
    suspend fun createConversation(
        @Part("data") data: RequestBody,
        @Part photo: MultipartBody.Part?
    ): ConversationIdResponse
}