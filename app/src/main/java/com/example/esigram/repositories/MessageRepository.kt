package com.example.esigram.repositories

import com.example.esigram.providers.FirebaseProvider
import com.example.esigram.services.ApiService
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.File

class MessageRepository {
    private val api = ApiService()

    @Suppress("UNCHECKED_CAST")
    suspend
    fun getAll(chatId: String): Map<String, Any> {
        val snapshot = FirebaseProvider.database
            .getReference("chats/$chatId/messages")
            .get()
            .await()

        return snapshot.value as? Map<String, Any> ?: emptyMap()
    }

    suspend fun createMessage(
        chatId: String,
        content: String,
        files: List<File>? = null
    ): HttpResponse {
        val jsonData = Json.encodeToString(buildJsonObject {
            put(
                "content",
                content
            )
        })

        return api.client.submitFormWithBinaryData(
            url = "${api.baseUrl}/chats/$chatId/messages",
            formData = formData {
                append(
                    "data",
                    jsonData,
                    Headers.build {
                        append(
                            HttpHeaders.ContentType,
                            ContentType.Application.Json
                        )
                    })

                files ?. forEach { file ->
                    append(
                        "attachments",
                        file.readBytes(),
                        Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=\"${file.name}\""
                            )
                            append (HttpHeaders.ContentType, ContentType.Image.Any.toString())
                        })
                }
            })
    }

    suspend fun deleteMessage(messageId: String): HttpResponse {
        return api.client.delete("${api.baseUrl}/messages/$messageId")
    }
}