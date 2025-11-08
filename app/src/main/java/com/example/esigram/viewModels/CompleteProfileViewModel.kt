package com.example.esigram.viewModels

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esigram.domains.usecase.user.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class CompleteProfileViewModel(private val useCases: UserUseCases): ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance().currentUser

    private val _description = MutableStateFlow<String?>("")
    val description = _description.asStateFlow()

    private val _file = MutableStateFlow<File?>(null)
    val file = _file.asStateFlow()

    private val _fileUri = MutableStateFlow<Uri?>(null)
    val fileUri = _fileUri.asStateFlow()

    private val _username = MutableStateFlow(firebaseAuth?.displayName ?: "")
    val username = _username.asStateFlow()

    private val _submitResult = MutableStateFlow<Boolean?>(null)
    val submitResult = _submitResult.asStateFlow()

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    // Set a default picture at start of screen
    fun setDefaultProfilePicture(context: Context) {
        if (_fileUri.value == null) {
            val defaultUri = Uri.parse("android.resource://${context.packageName}/drawable/default_picture")
            onFileChange(defaultUri, context)
        }
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

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun completeSignUp(){
        viewModelScope.launch {
            val response = useCases.registerUserToDBUseCase(
                username = username.value,
                description = description.value,
                file = file.value
            )

            _submitResult.value = response.isSuccess
        }
    }
}