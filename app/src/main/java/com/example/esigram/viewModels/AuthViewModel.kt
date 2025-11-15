package com.example.esigram.viewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.example.esigram.R
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.domains.usecase.auth.AuthUseCases
import com.example.esigram.domains.usecase.user.UserUseCases
import kotlinx.coroutines.launch

class AuthViewModel(private val authUseCases: AuthUseCases, private val userUseCases: UserUseCases, context: Context): ViewModel() {
    val sessionManager = SessionManager(context)
    private val _user = mutableStateOf(authUseCases.getCurrentUserUseCase())
    val user: State<FirebaseUser?> = _user

    fun refreshUser() {
        _user.value = authUseCases.getCurrentUserUseCase()
    }

    fun isUserLoggedIn(): Boolean {
        return authUseCases.getCurrentUserUseCase() != null
    }

    fun signOut() {
        viewModelScope.launch {
            sessionManager.clearSession()
            authUseCases.signOutUseCase()
            refreshUser()
        }

    }

    fun signIn(): Intent {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.FirebaseLoginTheme)
            .setLogo(R.drawable.logo_smaller)
            .build()
    }

    fun isNewUser(): Boolean {
        val user = authUseCases.getCurrentUserUseCase() ?: return false
        val creationTimestamp = user.metadata?.creationTimestamp ?: return false
        val lastSignInTimestamp = user.metadata?.lastSignInTimestamp ?: return false

        return creationTimestamp == lastSignInTimestamp
    }

    fun saveUserSession() {
        viewModelScope.launch {
            val userResponse = userUseCases.getMeCase()

            if(userResponse.isSuccessful) {
                val userData = userResponse.body()?.data

                userData?.let {
                    sessionManager.saveUserSession(
                        id = user.value!!.uid,
                        username = userData.username,
                        email = userData.email,
                        description = userData.description,
                        profilePictureUrl = userData.profilePictureUrl?.signedUrl
                    )
                }
            }
        }
    }
}