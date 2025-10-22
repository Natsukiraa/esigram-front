package com.example.esigram.mappers

import com.example.esigram.models.Conversation
import com.example.esigram.models.Message
import java.time.Instant
import com.example.esigram.models.User

object ConversationMapper {

    fun fromMap(id: String, data: Map<String, Any>): Conversation {
        val createdAt = (data["createdAt"] as? String)?.let {
            runCatching { Instant.parse(it) }.getOrElse { Instant.now() }
        } ?: Instant.now()

        val members = listOf(
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            ),
            User(
                id = "2",
                forename = "Lena",
                name = "Mabille",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        )

        val lastMessageMap = data["lastMessage"] as? Map<String, Any>
        val lastMessage = lastMessageMap?.let {
            Message(
                id = it["id"] as? String ?: "",
                sender = members.firstOrNull() ?: User("", "", ""),
                description = it["content"] as? String ?: "",
                createdAt = (it["createdAt"] as? String)?.let { str ->
                    runCatching { Instant.parse(str) }.getOrElse { Instant.now() }
                } ?: Instant.now(),
                attachments = emptyList()
            )
        }

        return Conversation(
            id = id,
            members = members,
            lastMessage = lastMessage,
            createdAt = createdAt,
            title = data["name"] as String?,
            coverImageId = data["coverImageId"] as? String
        )
    }
}