package com.example.esigram.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.esigram.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(private val repository: AuthRepository = AuthRepository()): ViewModel() {
    private val _user = mutableStateOf(repository.getCurrentUser())
    val user: State<FirebaseUser?> = _user

    fun refreshUser() {
        _user.value = repository.getCurrentUser()
    }
}