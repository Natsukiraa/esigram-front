package com.example.esigram.datas.remote

import android.util.Log
import com.example.esigram.datas.mappers.MessageMapper
import com.example.esigram.datas.remote.services.MessageApiService
import com.example.esigram.domains.models.Message
import com.example.esigram.networks.RetrofitInstance
import com.example.esigram.providers.FirebaseProvider
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.Instant

class MessageRemoteDataSource {
    private val api = RetrofitInstance.api
    private val messageService = api.create(MessageApiService::class.java)

    sealed class RealtimeMessageEvent {
        data class OnNewMessage(val message: Message) : RealtimeMessageEvent()
        data class OnRemoveMessage(val messageId: String) : RealtimeMessageEvent()
    }

    fun listenToMessages(
        chatId: String,
        currentUserId: String,
        lastDate: String?
    ): Flow<RealtimeMessageEvent> = callbackFlow {

        val ref = FirebaseProvider.database.getReference("messages/$chatId")

        val query = if (lastDate != null) {
            ref.orderByChild("createdAt").startAt(lastDate)
        } else {
            ref.orderByChild("createdAt")
        }

        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.value as? Map<String, Any> ?: return
                val id = snapshot.key ?: return
                val message = MessageMapper.fromMap(id, currentUserId, data)

                trySend(RealtimeMessageEvent.OnNewMessage(message))
            }

            override fun onChildChanged(snapshot: DataSnapshot, prev: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val id = snapshot.key ?: return
                trySend(RealtimeMessageEvent.OnRemoveMessage(id))
            }

            override fun onChildMoved(snapshot: DataSnapshot, prev: String?) {}
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        query.addChildEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun loadOlderMessages(
        chatId: String,
        currentUserId: String,
        beforeTimestamp: Long,
        lastMessageId: String,
        pageSize: Int = 20
    ): List<Message> {

        val instant = Instant.ofEpochMilli(beforeTimestamp)
        val isoString = instant.toString()

        val snapshot = FirebaseProvider.database.getReference("messages/$chatId")
            .orderByChild("createdAt")
            .endAt(isoString, lastMessageId)
            .limitToLast(pageSize)
            .get()
            .await()

        if (!snapshot.exists()) {
            Log.e("Pagination", "Snapshot is empty.")
            return emptyList()
        }

        snapshot.children.map { it.key }

        val result = snapshot.children.mapNotNull { child ->
            val data = child.value as? Map<String, Any> ?: return@mapNotNull null
            val id = child.key ?: return@mapNotNull null
            MessageMapper.fromMap(id, currentUserId, data)
        }
            .filter { it.id != lastMessageId }
            .sortedBy { it.createdAt }

        Log.d("Pagination", "After filtering, we have ${result.size} messages")

        return result.reversed()
    }

    suspend fun createMessage(
        chatId: String, content: String, files: List<java.io.File>? = null
    ): Result<Unit> {
        return try {
            val jsonData = Json.encodeToString(
                serializer = JsonObject.serializer(),
                value = buildJsonObject {
                    put("content", content)
                }
            )

            val requestBody = jsonData.toRequestBody("application/json".toMediaType())

            val filesPart = files?.map { file ->
                val fileReq = file.asRequestBody("*/*".toMediaType())
                MultipartBody.Part.createFormData(
                    "attachments", file.name, fileReq
                )
            }

            messageService.sendMessage(chatId, requestBody, filesPart)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteMessage(chatId: String, messageId: String): Result<Unit> {
        return try {
            val response = messageService.deleteMessage(chatId, messageId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}