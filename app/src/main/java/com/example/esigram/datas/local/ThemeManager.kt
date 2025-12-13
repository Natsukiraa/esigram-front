package com.example.esigram.datas.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.esigram.domains.models.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal val Context.ThemeDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class ThemeManager(private val context: Context) {

    private val THEME_KEY = stringPreferencesKey("theme_mode")

    fun getThemeMode(): Flow<ThemeMode> {
        return context.ThemeDataStore.data
            .map { preferences ->
                val themeString = preferences[THEME_KEY] ?: ThemeMode.System.name
                ThemeMode.valueOf(themeString)
            }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.ThemeDataStore.edit { settings ->
            settings[THEME_KEY] = mode.name
        }
    }
}