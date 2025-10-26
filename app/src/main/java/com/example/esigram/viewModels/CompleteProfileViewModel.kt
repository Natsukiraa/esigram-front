package com.example.esigram.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.repositories.AuthRepository
import com.example.esigram.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.logging.Logger

class CompleteProfileViewModel: ViewModel() {
    private val repository = UserRepository()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _description = MutableStateFlow<String?>("")
    val description = _description.asStateFlow()

    private val _file = MutableStateFlow<Uri?>(null)
    val file = _file.asStateFlow()

    private val _username = MutableStateFlow(firebaseAuth.currentUser?.displayName ?: "")
    val username = _username.asStateFlow()

    private val _submitResult = MutableStateFlow<Boolean?>(null)
    val submitResult = _submitResult.asStateFlow()

    private val _userExistsInPsql = MutableStateFlow<Boolean?>(null)
    val userExistsInPsql = _userExistsInPsql.asStateFlow()

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun onFileChange(newFile: Uri?) {
        _file.value = newFile
    }

    fun doesUserExistsInPsql(){
        viewModelScope.launch {
            val response = repository.getMe()
            _userExistsInPsql.value = response.status.value == 200
        }
    }

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun completeSignUp(){
        viewModelScope.launch {
            val response = repository.registerUserToDB(
                username = username.value,
                description = description.value,
                file = file.value
            )

            _submitResult.value = response.status.value == 200
        }
    }
}