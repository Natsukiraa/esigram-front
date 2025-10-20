package com.example.esigram.repositories

import com.example.esigram.models.Conversation
import com.example.esigram.models.Message
import com.example.esigram.models.User
import com.example.esigram.provider.FirebaseProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.time.format.DateTimeParseException

class ConversationRepository {

    private val database: FirebaseDatabase = FirebaseProvider.database

    suspend fun getAll(): List<Conversation> {
        val snapshot = database.getReference("chats").get().await()
        val conversations = mutableListOf<Conversation>()

        snapshot.children.forEach { child ->
            val id = child.key ?: return@forEach
            val data = child.value as? Map<String, Any> ?: emptyMap()
            conversations.add(parseConversation(id, data))
        }

        return conversations
    }

    suspend fun getById(id: String): Conversation? {
        val snapshot = database.getReference("chats").child(id).get().await()
        if (!snapshot.exists()) return null

        val data = snapshot.value as? Map<String, Any> ?: emptyMap()
        return parseConversation(id, data)
    }

    private fun parseConversation(id: String, data: Map<String, Any>): Conversation {
        val createdAtStr = data["createdAt"] as? String
        val createdAt = try {
            Instant.parse(createdAtStr)
        } catch (e: DateTimeParseException) {
            Instant.now()
        }

        val members = listOf(
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        )

        val lastMessageMap = data["lastMessage"] as? Map<*, *>
        val lastMessage = lastMessageMap?.let {
            Message(
                id = it["id"] as? String ?: "",
                sender = members[0],
                description = it["content"] as? String ?: "",
                createdAt = Instant.parse(it["createdAt"] as? String ?: Instant.now().toString()),
                attachments = emptyList()
            )
        }

        val name = data["name"] as? String ?: "Sans nom"
        val coverImageId = data["coverImageId"] as? String

        return Conversation(
            id = id,
            members = members,
            lastMessage = lastMessage,
            createdAt = createdAt,
            title = name,
            coverImageId = coverImageId
        )
    }
}
