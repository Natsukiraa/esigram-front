package com.example.esigram.viewModels

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.example.esigram.R
import com.example.esigram.domains.usecase.auth.AuthUseCases

class AuthViewModel(private val authUseCases: AuthUseCases): ViewModel() {
    private val _user = mutableStateOf(authUseCases.getCurrentUserUseCase())
    val user: State<FirebaseUser?> = _user

    fun refreshUser() {
        _user.value = authUseCases.getCurrentUserUseCase()
    }

    fun isUserLoggedIn(): Boolean {
        return authUseCases.getCurrentUserUseCase() != null
    }

    fun signOut(context: ComponentActivity) {
        authUseCases.signOutUseCase()
        refreshUser()
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
}