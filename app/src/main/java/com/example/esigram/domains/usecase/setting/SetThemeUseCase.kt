package com.example.esigram.domains.usecase.setting

import com.example.esigram.domains.models.ThemeMode
import com.example.esigram.domains.repositories.ThemeRepository

class SetThemeUseCase(private val repository: ThemeRepository) {
    suspend operator fun invoke(mode: ThemeMode) = repository.setThemeMode(mode)
}