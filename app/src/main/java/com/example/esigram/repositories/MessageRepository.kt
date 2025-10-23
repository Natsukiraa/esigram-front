package com.example.esigram.repositories

import com.example.esigram.providers.FirebaseProvider
import com.example.esigram.services.ApiService
import com.google.firebase.database.FirebaseDatabase
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MessageRepository {
    private val api = ApiService()
    private val database: FirebaseDatabase = FirebaseProvider.database

    suspend fun getAll(chatId: String): List<String> = suspendCoroutine { continuation ->
        val ref = FirebaseDatabase.getInstance()
            .getReference("messages")
            .child(chatId)

        ref.get()
            .addOnSuccessListener { snapshot ->
                val messageIds = snapshot.children.mapNotNull { it.key }
                continuation.resume(messageIds)
            }
            .addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
    }
    suspend fun createMessage(chatId: String, content: String, files: List<File>? = null): HttpResponse {
        val jsonData = Json.encodeToString(
            buildJsonObject {
                put("content", content)
            }
        )

        return api.client.submitFormWithBinaryData(
            url = "${api.baseUrl}/chats/$chatId/messages",
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
        return api.client.delete("${api.baseUrl}/messages/$messageId")
    }
}
