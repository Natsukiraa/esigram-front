package com.example.esigram.viewModels

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.datas.local.SessionManager
import com.example.esigram.domains.usecase.user.UserUseCases
import com.example.esigram.viewModels.utils.onFileChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(
    private val useCases: UserUseCases,
    private val context: Context
) : ViewModel() {
    private val sessionManager = SessionManager(context)

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _profilePictureUrl = MutableStateFlow("")
    val profilePictureUrl = _profilePictureUrl.asStateFlow()

    private val _file = MutableStateFlow<File?>(null)
    val file = _file.asStateFlow()

    private val _fileUri = MutableStateFlow<Uri?>(null)
    val fileUri = _fileUri.asStateFlow()

    private val _isEditing = MutableStateFlow(false)
    val isEditing = _isEditing.asStateFlow()

    init {
        combine(
            sessionManager.username,
            sessionManager.email,
            sessionManager.description,
            sessionManager.profilePictureUrl
        ) { username, email, description, profilePictureUrl ->
            _username.value = username
            _email.value = email
            _description.value = description ?: ""
            _profilePictureUrl.value = profilePictureUrl ?: ""
        }.launchIn(viewModelScope)
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onEdit() {
        _isEditing.value = true
    }

    fun onFileChangeUtil(newFile: Uri, context: Context) {
        onFileChange(newFile, context, _fileUri, _file)
    }

    fun onSave() {
        viewModelScope.launch {
            val response = useCases.patchUserUseCase(
                username = username.value,
                description = description.value,
                file = file.value
            )

            if (response.isSuccess) {
                val userResponse = response.getOrNull()
                val newUserProfile = userResponse?.data

                newUserProfile?.let {
                    sessionManager.updateUserSession(
                        username = newUserProfile.username,
                        description = newUserProfile.description,
                        profilePictureUrl = newUserProfile.profilePictureUrl?.signedUrl
                    )

                    Toast.makeText(context, "Profile updated successfully !", Toast.LENGTH_SHORT)
                        .show()
                    _isEditing.value = false

                    clearProfilePictureData()
                }
            }
        }
    }

    fun clearProfilePictureData() {
        _file.value = null
        _fileUri.value = null
    }
}


