package com.example.esigram.repositories

import com.example.esigram.mappers.ConversationMapper
import com.example.esigram.models.Conversation
import com.example.esigram.providers.FirebaseProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ConversationRepository {

    private val database: FirebaseDatabase = FirebaseProvider.database
    private val userId: String = "0EVICHYX64fhkzxQ1dAPMKzpLRC2"

    suspend fun getAll(): List<String> = suspendCoroutine { continuation ->
        val ref = FirebaseDatabase.getInstance()
            .getReference("user_chats")
            .child(userId)

        ref.get()
            .addOnSuccessListener { snapshot ->
                val conversationIds = snapshot.children.mapNotNull { it.key }
                continuation.resume(conversationIds)
            }
            .addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
    }

    suspend fun getById(id: String): Conversation? {
        val snapshot = database.getReference("chats").child(id).get().await()
        if (!snapshot.exists()) return null

        val data = snapshot.value as? Map<String, Any> ?: emptyMap()
        return ConversationMapper.fromMap(id, data)
    }

}
