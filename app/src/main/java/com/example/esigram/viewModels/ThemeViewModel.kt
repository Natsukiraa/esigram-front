package com.example.esigram.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.models.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel() : ViewModel() {
    private val _currentTheme = MutableStateFlow(ThemeMode.System)
    val currentTheme: StateFlow<ThemeMode> = _currentTheme.asStateFlow()

    fun saveTheme(newTheme: ThemeMode) {
        viewModelScope.launch {
            _currentTheme.value = newTheme
        }
    }
}