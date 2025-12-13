package com.example.esigram.domains.usecase.setting

import com.example.esigram.domains.models.ThemeMode
import com.example.esigram.domains.repositories.ThemeRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(private val repository: ThemeRepository) {
    operator fun invoke(): Flow<ThemeMode> = repository.getThemeMode()
}