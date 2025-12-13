package com.example.esigram.viewModels.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

fun onFileChange(
    newFile: Uri,
    context: Context,
    _fileUri: MutableStateFlow<Uri?>,
    _file: MutableStateFlow<File?>
) {
    val contentResolver: ContentResolver = context.contentResolver
    val mime: MimeTypeMap = MimeTypeMap.getSingleton()
    val mimeType = mime.getExtensionFromMimeType(contentResolver.getType(newFile))

    _fileUri.value = newFile

    val inputStream = context.contentResolver.openInputStream(newFile)

    inputStream?.let {
        val tmpFile = File.createTempFile("profilePicture", ".$mimeType", context.cacheDir)
        tmpFile.outputStream().use { outputStream ->
            it.copyTo(outputStream)
        }
        _file.value = tmpFile
    }
}

fun uriToFile(context: Context, uri: Uri): File? {
    try {
        val contentResolver = context.contentResolver

        val mimeType = contentResolver.getType(uri)
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"

        val inputStream = contentResolver.openInputStream(uri) ?: return null

        val tempFile = File.createTempFile("media_upload", ".$extension", context.cacheDir)

        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}