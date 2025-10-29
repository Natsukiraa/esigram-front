package com.example.esigram.viewModels

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.Destinations
import com.example.esigram.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class CompleteProfileViewModel: ViewModel() {
    private val repository = UserRepository()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _description = MutableStateFlow<String?>("")
    val description = _description.asStateFlow()

    private val _file = MutableStateFlow<File?>(null)
    val file = _file.asStateFlow()

    private val _fileUri = MutableStateFlow<Uri?>(null)
    val fileUri = _fileUri.asStateFlow()

    private val _username = MutableStateFlow(firebaseAuth.currentUser?.displayName ?: "")
    val username = _username.asStateFlow()

    private val _submitResult = MutableStateFlow<Boolean?>(null)
    val submitResult = _submitResult.asStateFlow()

    private val _destination = MutableStateFlow<String?>(null)
    val destination = _destination.asStateFlow()

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun onFileChange(newFile: Uri, context: Context) {
        // Used to get mime type from URI
        val contentResolver: ContentResolver = context.contentResolver
        val mime: MimeTypeMap = MimeTypeMap.getSingleton()
        val mimeType = mime.getExtensionFromMimeType(contentResolver.getType(newFile))

        _fileUri.value = newFile

        // Copy URI content into temporary file
        val inputStream =  context.contentResolver.openInputStream(newFile)

        inputStream?.let {
            val tmpFile = File.createTempFile("profilePicture", ".$mimeType", context.cacheDir)
            tmpFile.outputStream().use { outputStream ->
                it.copyTo(outputStream)
            }
            _file.value = tmpFile
        }
    }

    fun doesUserExistsInPsql(){
        viewModelScope.launch {
            val response = repository.getMe()
            _destination.value = if (response.status.value == 200) {
                Destinations.HOME
            } else {
                Destinations.COMPLETE_PROFILE
            }
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