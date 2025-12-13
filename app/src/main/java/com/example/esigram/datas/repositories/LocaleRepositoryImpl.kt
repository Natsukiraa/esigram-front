package com.example.esigram.datas.repositories

import com.example.esigram.datas.local.LocaleManager
import com.example.esigram.domains.repositories.LocaleRepository
import kotlinx.coroutines.flow.Flow

class LocaleRepositoryImpl(private val localeManager: LocaleManager) : LocaleRepository {
    override fun getLanguageCode(): Flow<String> {
        return localeManager.getLanguageCode()
    }

    override suspend fun setLanguageCode(code: String) {
        localeManager.setLanguageCode(code)
    }
}