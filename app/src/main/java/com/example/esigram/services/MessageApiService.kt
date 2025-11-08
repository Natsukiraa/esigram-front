package com.example.esigram.services

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST

interface MessageApiService {
    @Multipart
    @POST("/chats/{chatId}/messages")
    //TODO suspend fun sendMessage(chatId: String): Response<Unit>

    @DELETE("/messages/{id}")
    suspend fun deleteMessage(id: String): Response<Unit>
}