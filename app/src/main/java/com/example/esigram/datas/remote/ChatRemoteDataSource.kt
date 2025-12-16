package com.example.esigram.datas.remote

import com.example.esigram.datas.remote.services.ChatApiService
import com.example.esigram.domains.models.responses.ChatResponse
import com.example.esigram.networks.RetrofitInstance

class ChatRemoteDataSource {
    private val api = RetrofitInstance.api

    private val chatService = api.create(ChatApiService::class.java)

    suspend fun getChatById(id: String): ChatResponse? {
        val r = chatService.getChatById(id)
        if(r.isSuccessful) return r.body()?.data
        else throw Exception("Error ${r.code()}")

    }
}