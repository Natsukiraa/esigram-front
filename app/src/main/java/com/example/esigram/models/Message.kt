package com.example.esigram.models

import java.time.Instant
import java.util.UUID

data class Message(
    var id: UUID,
    var description: String,
    var createdAt: Long = Instant.now().epochSecond,
    var colorIndex: Int,
    var seen: Boolean
)