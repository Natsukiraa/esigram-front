package com.example.esigram.viewModels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.esigram.domains.usecase.user.UserUseCases
import com.example.esigram.viewModels.ProfileViewModel

class ProfileViewModelFactory(
    private val userUseCases: UserUseCases,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userUseCases, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}