package com.example.esigram.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.esigram.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class CompleteProfileViewModel: ViewModel() {
    private val repository = AuthRepository()

    private val _description = MutableStateFlow<String?>("")
    val description = _description.asStateFlow()

    private val _file = MutableStateFlow<Uri?>(null)
    val file = _file.asStateFlow()

    private val _submitResult = MutableStateFlow<Boolean?>(null)
    val submitResult = _submitResult.asStateFlow()

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun onFileChange(newFile: Uri?) {
        _file.value = newFile
    }

    suspend fun completeSignUp(){
        val username = repository.getCurrentUser()?.displayName

        val response = repository.registerUserToDB(
            username = username ?: "",
            description = description.value,
            file = file.value
        )

        _submitResult.value = response.status.value == 200
    }

}