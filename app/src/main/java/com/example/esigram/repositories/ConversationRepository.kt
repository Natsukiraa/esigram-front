package com.example.esigram.repositories

import com.example.esigram.models.Conversation
import com.example.esigram.models.Message
import com.example.esigram.models.User
import java.time.Instant
import java.time.temporal.ChronoUnit

class ConversationRepository {
        private val userArthur = User(
            id = "1",
            forename = "Arthur",
            name = "Morelon",
            image = "https://randomuser.me/api/portraits/men/1.jpg",
            isOnline = true
        )

        private val userLina = User(
            id = "2",
            forename = "Lina",
            name = "Dupont",
            image = "https://randomuser.me/api/portraits/women/2.jpg",
            isOnline = false
        )

        private val userAlex = User(
            id = "3",
            forename = "Alex",
            name = "Martin",
            image = "https://randomuser.me/api/portraits/men/3.jpg",
            isOnline = true
        )

        private val userNora = User(
            id = "4",
            forename = "Nora",
            name = "Durand",
            image = "https://randomuser.me/api/portraits/women/4.jpg",
            isOnline = false
        )

        val conversations = mutableListOf<Conversation>(
            Conversation(
                id = "conv1",
                participants = listOf(userArthur, userLina),
                lastMessage = Message(
                    id = "msg1",
                    description = "Salut, tu fais quoi ce soir ?",
                    sender = userLina,
                    createdAt = Instant.now().minus(2, ChronoUnit.HOURS),
                    colorIndex = 1
                ),
                unreadCount = 1,
                isGroup = false,
                createdAt = Instant.now().minus(1, ChronoUnit.DAYS)
            ),

            Conversation(
                id = "conv2",
                participants = listOf(userArthur, userAlex),
                lastMessage = Message(
                    id = "msg2",
                    description = "On se voit demain pour le projet ?",
                    sender = userAlex,
                    createdAt = Instant.now().minus(1, ChronoUnit.DAYS),
                    colorIndex = 1,
                ),
                unreadCount = 0,
                isGroup = false,
                createdAt = Instant.now().minus(2, ChronoUnit.DAYS)
            ),

            Conversation(
                id = "conv3",
                participants = listOf(userArthur, userNora),
                lastMessage = Message(
                    id = "msg3",
                    description = "Merci encore pour ton aide 🙏",
                    sender = userArthur,
                    createdAt = Instant.now().minus(3, ChronoUnit.DAYS),
                    colorIndex = 1,
                ),
                unreadCount = 0,
                isGroup = false,
                createdAt = Instant.now().minus(3, ChronoUnit.DAYS)
            ),

            Conversation(
                id = "conv4",
                participants = listOf(userArthur, userLina, userAlex, userNora),
                lastMessage = Message(
                    id = "msg4",
                    description = "Bon, on valide la date pour samedi ?",
                    sender = userAlex,
                    createdAt = Instant.now().minus(6, ChronoUnit.HOURS),
                    colorIndex = 1
                ),
                unreadCount = 3,
                isGroup = true,
                title = "Soirée du week-end 🎉",
                createdAt = Instant.now().minus(5, ChronoUnit.DAYS)
            ),

            Conversation(
                id = "conv5",
                participants = listOf(userArthur, userNora),
                lastMessage = null,
                unreadCount = 0,
                isGroup = false,
                createdAt = Instant.now()
            )
        )


    fun getAll(): List<Conversation> = conversations

    fun getById(id: String): Conversation? = conversations.find{ it.id == id }

    fun addNote(conversation:Conversation) = conversations.add(conversation)

    fun deleteNote(conversation:Conversation) = conversations.removeIf { it.id == conversation.id }

}