package com.example.esigram.domains.usecase.setting

data class SettingUseCases(
    val getThemeMode: GetThemeUseCase,
    val setThemeMode: SetThemeUseCase,
    val getLocale: GetLocaleUseCase,
    val setLocale: SetLocaleUseCase
)