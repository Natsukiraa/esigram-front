package com.example.esigram.datas.remote.services

import com.example.esigram.domains.models.responses.ChatDto
import com.example.esigram.domains.models.responses.ChatResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatApiService {
    @GET("chats/{id}")
    suspend fun getChatById(
        @Path("id") id: String
    ): Response<ChatDto>
}