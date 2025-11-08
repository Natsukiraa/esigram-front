package com.example.esigram.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.models.CorrectUser
import com.example.esigram.domains.usecase.user.UserUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userUseCases: UserUseCases): ViewModel() {
    private val _me = MutableStateFlow<CorrectUser?>(null)
    val me = _me.asStateFlow()

    init {
        getMe()
    }

    fun getMe() {
        viewModelScope.launch {
            val response = userUseCases.getMeCase()
            if(response.isSuccessful){
                _me.value = response.body()?.data
            }
        }
    }

}

