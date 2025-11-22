package com.example.esigram.domains.models

import android.net.Uri

data class CapturedMedia(
    val uri: Uri,
    val isVideo: Boolean
)