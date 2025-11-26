package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.Media

interface MediaRepository {
    suspend fun getMedia(id: String): Media?

}