package com.example.esigram.domains.repositories

import kotlinx.coroutines.flow.Flow

interface LocaleRepository {
    fun getLanguageCode(): Flow<String>
    suspend fun setLanguageCode(code: String)
}