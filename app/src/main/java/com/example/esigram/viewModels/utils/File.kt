package com.example.esigram.viewModels.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

fun onFileChange(newFile: Uri, context: Context, _fileUri: MutableStateFlow<Uri?>, _file: MutableStateFlow<File?>) {
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