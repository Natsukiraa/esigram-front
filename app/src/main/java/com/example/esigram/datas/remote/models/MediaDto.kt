package com.example.esigram.datas.remote.models


data class MediaResponseDto(
    val data: MediaDto
)

data class MediaDto(
    val id: String,
    val signedUrl: String
)