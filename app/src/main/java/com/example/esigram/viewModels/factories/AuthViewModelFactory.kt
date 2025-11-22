package com.example.esigram.viewModels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esigram.domains.usecase.auth.AuthUseCases
import com.example.esigram.domains.usecase.user.UserUseCases
import com.example.esigram.viewModels.AuthViewModel

class AuthViewModelFactory(
    private val authUseCases: AuthUseCases,
    private val userUseCases: UserUseCases,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authUseCases, userUseCases, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}