package com.example.esigram.domains.models.responses

import com.example.esigram.domains.models.Media
import com.example.esigram.domains.models.User
import java.time.Instant

data class ChatResponse(
    val id: String,
    val name: String?,
    val coverImage: Media?,
    val members: List<User>,
)

data class ChatDto(
    val data: ChatResponse
)
