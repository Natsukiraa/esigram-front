package com.example.esigram.datas.remote

import android.util.Log
import com.example.esigram.datas.remote.models.ConversationDto
import com.example.esigram.datas.remote.models.ConversationIdResponse
import com.example.esigram.datas.remote.models.CreateConversation
import com.example.esigram.datas.remote.models.CreateConversationRequest
import com.example.esigram.datas.remote.services.ConversationApiService
import com.example.esigram.datas.remote.services.FriendApiService
import com.example.esigram.networks.RetrofitInstance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

class ConversationRemoteDataSource(
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
) {

    private val api = RetrofitInstance.api
    private val conversationService = api.create(ConversationApiService::class.java)

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

    suspend fun createConversation(conversation: CreateConversation): ConversationIdResponse? {
        return try {
            Log.d("conversation", "create")

            val json = Gson().toJson(conversation)
            val body = json.toRequestBody("application/json".toMediaType())

            val res = conversationService.createConversation(body, null)

            Log.d("conversation", "Success: $res")

            res
        } catch (e: HttpException) {
            Log.e(
                "conversation",
                "HTTP error: ${e.code()} ${e.response()?.errorBody()?.string()}"
            )
            null
        } catch (e: IOException) {
            Log.e("conversation", "Network error: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("conversation", "Unknown error: ${e.stackTraceToString()}")
            null
        }
    }
}