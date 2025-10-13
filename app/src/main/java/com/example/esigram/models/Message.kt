package com.example.esigram.models

import java.time.Instant

data class Message(
    val id: Int,
    val text: String,
    val createdAt: Instant
)
