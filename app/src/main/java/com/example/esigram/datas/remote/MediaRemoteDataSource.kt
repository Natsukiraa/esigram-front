package com.example.esigram.datas.remote

import com.example.esigram.datas.mappers.toDomain
import com.example.esigram.datas.remote.services.MediaApiService
import com.example.esigram.domains.models.Media
import com.example.esigram.networks.RetrofitInstance

class MediaRemoteDataSource {
    private val api = RetrofitInstance.api
    private val mediaService = api.create(MediaApiService::class.java)

    suspend fun getMedia(id: String): Media? {
        val response = mediaService.getMediaById(id)

        if(response.isSuccessful) {
           return response.body()?.data?.toDomain()
        }
        return null
    }
}