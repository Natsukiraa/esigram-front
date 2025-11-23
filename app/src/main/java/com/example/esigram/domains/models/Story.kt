package com.example.esigram.domains.models

import java.time.Instant

data class Story(
    var id: String,
    var media: Media,
    var viewers: List<TmpUser>,
    var author: TmpUser?,
    var createdAt: Instant,
    var expirationAt: Instant
)

data class CreateStory(
    var media: CapturedMedia
)