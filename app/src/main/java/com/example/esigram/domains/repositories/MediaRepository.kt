package com.example.esigram.domains.repositories

import com.example.esigram.models.Media
import retrofit2.Response

interface MediaRepository {
    suspend fun getMedia(id: String): Media?

}