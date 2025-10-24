package com.example.esigram.mappers

import com.example.esigram.models.Message
import java.time.Instant

object MessageMapper {

    fun fromMap(id: String, data: Map<String, Any>): Message {

        val attachments = (data["attachments"] as? List<*>)?.mapNotNull { it.toString() } ?: emptyList()
        val authorId = data["authorId"].toString()
        val content = data["content"].toString()
        val createdAt = Instant.parse(data["createdAt"].toString())

        return Message(
            id = id,
            authorId = authorId,
            content = content,
            createdAt = createdAt,
            attachments = attachments
        )
    }
}
