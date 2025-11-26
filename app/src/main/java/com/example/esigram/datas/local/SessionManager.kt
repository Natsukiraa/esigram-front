package com.example.esigram.datas.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
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
        val COMPLETED_ONBOARDING = booleanPreferencesKey("completedOnboarding")
    }

    suspend fun saveUserSession(
        id: String,
        username: String,
        email: String,
        description: String?,
        profilePictureUrl: String?,
        completedOnboarding: Boolean = false
    ) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = id
            preferences[USERNAME] = username
            preferences[EMAIL] = email
            description?.let { preferences[DESCRIPTION] = it }
            profilePictureUrl?.let { preferences[PROFILE_PICTURE_URL] = it
            preferences[COMPLETED_ONBOARDING] = completedOnboarding
            }
        }
    }

    suspend fun updateUserSession(
        username: String,
        description: String?,
        profilePictureUrl: String?
    ) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = username
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
        preferences[PROFILE_PICTURE_URL]?.replace("localhost", "10.184.30.28")
            ?: "android.resource://${context.packageName}/drawable/default_picture"
    }

    val completedOnboarding: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[COMPLETED_ONBOARDING] ?: false
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}