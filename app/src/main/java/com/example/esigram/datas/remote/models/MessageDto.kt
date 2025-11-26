package com.example.esigram.datas.remote.models

import java.time.Instant

data class MessageDto(
    val id: String,
    val data: Map<*, *>
) {
    val authorId: String?
        get() = data["authorId"] as? String

    val content: String?
        get() = data["content"] as? String

    val createdAt: Instant?
        get() = data["createdAt"] as? Instant
}