package com.example.esigram.viewModels.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream

fun uriToFile(context: Context, uri: Uri): File? {
    try {
        val contentResolver = context.contentResolver

        val mimeType = contentResolver.getType(uri)

        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "tmp"

        val inputStream = contentResolver.openInputStream(uri) ?: return null

        val tempFile = File.createTempFile("upload_media_", ".$extension", context.cacheDir)

        inputStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}