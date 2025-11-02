package com.example.esigram.models

import android.net.Uri

data class CapturedMedia(
    val uri: Uri,
    val isVideo: Boolean
)
