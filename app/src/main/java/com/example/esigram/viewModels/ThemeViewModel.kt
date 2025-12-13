package com.example.esigram.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.models.ThemeMode
import com.example.esigram.domains.usecase.setting.SettingUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val settingUseCases: SettingUseCases
) : ViewModel() {
    val currentTheme: StateFlow<ThemeMode> = settingUseCases.getThemeMode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeMode.System
        )

    fun saveTheme(newTheme: ThemeMode) {
        viewModelScope.launch {
            // Appel du Use Case pour la persistance
            settingUseCases.setThemeMode(newTheme)
        }
    }
}


class ThemeViewModelFactory(
    private val settingUseCases: SettingUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(settingUseCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}