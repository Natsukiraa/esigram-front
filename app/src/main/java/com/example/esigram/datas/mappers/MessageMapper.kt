package com.example.esigram.datas.mappers

import com.example.esigram.domains.models.Message
import java.time.Instant

object MessageMapper {

    fun fromMap(messageId: String, currentUserId: String, data: Map<String, Any>): Message {

        val attachments =
            (data["attachments"] as? List<*>)?.mapNotNull { it.toString() } ?: emptyList()
        val authorId = data["authorId"].toString()
        val content = data["content"].toString()
        val createdAt = Instant.parse(data["createdAt"].toString())
        val colorIndex: Int = if (authorId == currentUserId) {
            2
        } else {
            1
        }

        return Message(
            id = messageId,
            authorId = authorId,
            content = content,
            createdAt = createdAt,
            attachments = attachments,
            colorIndex = colorIndex
        )
    }
}
