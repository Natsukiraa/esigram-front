package com.example.esigram.datas.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.sessionDataStore by preferencesDataStore(name = "user_session")

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
        context.sessionDataStore.edit { preferences ->
            preferences[USER_ID] = id
            preferences[USERNAME] = username
            preferences[EMAIL] = email
            description?.let { preferences[DESCRIPTION] = it }
            profilePictureUrl?.let { preferences[PROFILE_PICTURE_URL] = it }
        }
    }

    suspend fun updateUserSession(
        username: String,
        description: String?,
        profilePictureUrl: String?
    ) {
        context.sessionDataStore.edit { preferences ->
            preferences[USERNAME] = username
            description?.let { preferences[DESCRIPTION] = it }
            profilePictureUrl?.let { preferences[PROFILE_PICTURE_URL] = it }
        }
    }

    val id: Flow<String> = context.sessionDataStore.data.map { preferences ->
        preferences[USER_ID] ?: ""
    }

    val username: Flow<String> = context.sessionDataStore.data.map { preferences ->
        preferences[USERNAME] ?: ""
    }

    val email: Flow<String> = context.sessionDataStore.data.map { preferences ->
        preferences[EMAIL] ?: ""
    }

    val description: Flow<String?> = context.sessionDataStore.data.map { preferences ->
        preferences[DESCRIPTION]
    }

    val profilePictureUrl: Flow<String?> = context.sessionDataStore.data.map { preferences ->
        preferences[PROFILE_PICTURE_URL]
            ?: "android.resource://${context.packageName}/drawable/default_picture"
    }

    suspend fun clearSession() {
        context.sessionDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}