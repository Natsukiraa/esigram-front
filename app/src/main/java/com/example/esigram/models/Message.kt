package com.example.esigram.models

import java.time.Instant

data class Message(
    var id: String,
    var authorId: String,
    var content: String,
    var createdAt: String = Instant.now().toString(),
    var colorIndex: Int = 1,
    var seen: Boolean = false,
    var attachments: List<String>? = null
)