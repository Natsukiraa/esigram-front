package com.example.esigram.domains.usecase.setting

import com.example.esigram.domains.repositories.LocaleRepository
import kotlinx.coroutines.flow.Flow

class GetLocaleUseCase(private val repository: LocaleRepository) {
    operator fun invoke(): Flow<String> = repository.getLanguageCode()
}