package com.example.esigram.models

import java.time.Instant

class Story (
    var id: String,
    var author: User,

    var media: Media,
    var viewers: List<User>,
    var createdAt: Instant,
    val isSeen: Boolean
)