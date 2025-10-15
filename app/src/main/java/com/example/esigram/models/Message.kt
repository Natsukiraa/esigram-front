package com.example.esigram.models

import java.time.Instant

data class Message(
    var id: String,
    var description: String,
    var createdAt: Instant = Instant.now(),
    var colorIndex: Int,
    var seen: Boolean = false,
    var sender: User
)