package com.example.esigram.datas.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {
    companion object {
        val USER_ID = stringPreferencesKey("id")
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val DESCRIPTION = stringPreferencesKey("description")
        val PROFILE_PICTURE_URL = stringPreferencesKey("profilePictureUrl")
    }

    suspend fun saveUserSession(
        id: String,
        username: String,
        email: String,
        description: String?,
        profilePictureUrl: String?
    ) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = id
            preferences[USERNAME] = username
            preferences[EMAIL] = email
            description?.let { preferences[DESCRIPTION] = it }
            profilePictureUrl?.let { preferences[PROFILE_PICTURE_URL] = it }
        }
    }

    val id: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_ID] ?: ""
    }

    val username: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME] ?: ""
    }

    val email: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[EMAIL] ?: ""
    }

    val description: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[DESCRIPTION]
    }

    val profilePictureUrl: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PROFILE_PICTURE_URL]?.replace("localhost", "192.168.3.54") ?: "android.resource://${context.packageName}/drawable/default_picture"
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}