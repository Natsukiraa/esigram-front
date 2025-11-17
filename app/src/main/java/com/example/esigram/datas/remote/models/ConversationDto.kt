package com.example.esigram.datas.remote.models

import java.time.Instant

data class ConversationDto(
    val id: String,
    val data: Map<*, *>
) {
    val title: String?
        get() = data["title"] as? String

    val memberIds: List<String>
        get() = data["memberIds"] as? List<String> ?: emptyList()

    val lastMessage: MessageDto?
        get() {
            val raw = data["lastMessage"] as? Map<*, *> ?: return null
            return MessageDto(
                id = raw["id"] as? String ?: "",
                data = raw
            )
        }

    val unreadCount: Int?
        get() = (data["unreadCount"] as? Long)?.toInt()

    val createdAt: Instant?
        get() = data["createdAt"] as? Instant
}