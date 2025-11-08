package com.example.esigram.viewModels

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.esigram.repositories.AuthRepository
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(): ViewModel() {
    private val repository = AuthRepository()

    private val _user = mutableStateOf(repository.getCurrentUser())
    val user: State<FirebaseUser?> = _user

    fun refreshUser() {
        _user.value = repository.getCurrentUser()
    }

    fun isUserLoggedIn(): Boolean {
        return repository.getCurrentUser() != null
    }

    fun signOut(context: ComponentActivity) {
        repository.signOut()
        refreshUser()
    }

    fun signIn(): Intent{
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
    }

    fun getUserIdToken(onResult: (String?) -> Unit) {
        repository.getUserIdToken { token ->
            onResult(token)
        }
    }
}