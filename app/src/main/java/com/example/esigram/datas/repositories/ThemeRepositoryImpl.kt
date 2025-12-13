package com.example.esigram.datas.repositories

import com.example.esigram.datas.local.ThemeManager
import com.example.esigram.domains.models.ThemeMode
import com.example.esigram.domains.repositories.ThemeRepository
import kotlinx.coroutines.flow.Flow


class ThemeRepositoryImpl(private val themeManager: ThemeManager) : ThemeRepository {

    override fun getThemeMode(): Flow<ThemeMode> {
        return themeManager.getThemeMode()
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        themeManager.setThemeMode(mode)
    }
}