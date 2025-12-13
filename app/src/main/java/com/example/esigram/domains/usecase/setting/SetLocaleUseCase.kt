package com.example.esigram.domains.usecase.setting

import com.example.esigram.domains.repositories.LocaleRepository

class SetLocaleUseCase(private val repository: LocaleRepository) {
    suspend operator fun invoke(code: String) = repository.setLanguageCode(code)
}