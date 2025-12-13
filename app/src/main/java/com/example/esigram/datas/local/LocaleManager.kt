package com.example.esigram.datas.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.localeDataStore by preferencesDataStore(name = "user_settings")

class LocaleManager(private val context: Context) {

    private val LANGUAGE_KEY = stringPreferencesKey("app_language_code")

    fun getLanguageCode(): Flow<String> {
        return context.localeDataStore.data
            .map { preferences ->
                preferences[LANGUAGE_KEY] ?: "system"
            }
    }

    suspend fun setLanguageCode(code: String) {
        context.localeDataStore.edit { settings ->
            settings[LANGUAGE_KEY] = code
        }
    }
}