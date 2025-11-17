package com.example.esigram.datas.remote

import android.util.Log
import com.example.esigram.datas.remote.models.ConversationDto
import com.example.esigram.networks.RetrofitInstance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import kotlinx.coroutines.tasks.await

class ConversationRemoteDataSource(
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
) {
    suspend fun getAll(userId: String): List<String> {
        val ref = database.getReference("user_chats").child(userId)
        val snapshot = ref.get().await()
        return snapshot.children.mapNotNull { it.key }
    }

    suspend fun getById(id: String): ConversationDto? {
        val snapshot = database.getReference("chats").child(id).get().await()
        if (!snapshot.exists()) return null

        val data = snapshot.value as? Map<*, *> ?: return null
        return ConversationDto(id = id, data = data)
    }

    fun observeConversation(id: String): Flow<ConversationDto?> = callbackFlow {
        val ref = database.getReference("chats").child(id)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value as? Map<*, *> ?: return
                trySend(ConversationDto(id = id, data = data))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }
}