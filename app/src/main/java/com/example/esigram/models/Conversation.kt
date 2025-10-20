package com.example.esigram.models

import java.time.Instant

data class Conversation(
    val id: String,
    val members: List<User>,
    val coverImageId: String? = null,
    val lastMessage: Message? = null,
    val unreadCount: Int = 0,
    val isGroup: Boolean = false,
    val title: String? = null,
    val createdAt: Instant,
)