package com.example.esigram.repositories

import android.util.Log
import com.example.esigram.mappers.MessageMapper
import com.example.esigram.models.Message
import com.example.esigram.providers.FirebaseProvider
import com.example.esigram.services.ApiService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.File

class MessageRepository {
    private val api = ApiService()
    fun listenMessages(chatId: String): Flow<List<Message>> = callbackFlow {
        val ref = FirebaseProvider.database.getReference("messages/$chatId")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = snapshot.children.mapNotNull { child ->
                    val data = child.value as? Map<String, Any> ?: return@mapNotNull null
                    val id = child.key ?: return@mapNotNull null
                    MessageMapper.fromMap(
                        messageId = id,
                        currentUserId = "EpFP0iC79pUwsWmeIBle2OHQNAv2",
                        data = data
                    )
                }
                    .sortedBy { it.createdAt }

                trySend(messages).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    suspend fun createMessage(
        chatId: String,
        content: String,
        files: List<File>? = null
    ): HttpResponse {
        val jsonData = Json.encodeToString(
            serializer = kotlinx.serialization.json.JsonObject.serializer(),
            value = buildJsonObject {
                put("content", content)
            }
        )

        return api.client.submitFormWithBinaryData(
            url = "chats/$chatId/messages",
            formData = formData {
                append(
                    "data",
                    jsonData,
                    Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                )

                files?.forEach { file ->
                    append(
                        "attachments",
                        file.readBytes(),
                        Headers.build {
                            append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                            append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                        }
                    )
                }
            }
        )
    }

    suspend fun deleteMessage(messageId: String): HttpResponse {
        return api.client.delete("messages/$messageId")
    }
}
