package com.example.esigram.domains.models

import java.time.Instant

data class Conversation(
    val id: String,
    val members: List<User>,
    val coverImageId: String? = null,
    val lastMessage: Message? = null,
    val unreadCount: Int = 0,
    val title: String? = null,
    val createdAt: Instant,
) {
    val isGroup: Boolean
        get() = members.size > 2
}