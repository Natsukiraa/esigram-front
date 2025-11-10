package com.example.esigram.datas.remote

import com.example.esigram.datas.mappers.ConversationMapper
import com.example.esigram.domains.models.Conversation
import com.example.esigram.providers.FirebaseProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ConversationRemoteDataSource {
    // mettre la logique metier ici
    private val database: FirebaseDatabase = FirebaseProvider.database
    private val userId: String = "0EVICHYX64fhkzxQ1dAPMKzpLRC2"

    /*
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

    fun observeConversation(id: String): Flow<Conversation?> = callbackFlow {
        val ref = database.getReference("chats").child(id)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value as? Map<String, Any> ?: emptyMap()
                trySend(ConversationMapper.fromMap(id, data))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }
     */

    suspend fun getAll(): List<String> {
        return emptyList()
    }

    suspend fun getById(id: String): Conversation? {
        return null
    }

    fun observeConversation(id: String): Flow<Conversation?> {
        return callbackFlow {
            val ref = database.getReference("chats").child(id)
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.value as? Map<String, Any> ?: emptyMap()
                    trySend(ConversationMapper.fromMap(id, data))
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            ref.addValueEventListener(listener)
            awaitClose { ref.removeEventListener(listener) }
        }
    }
}