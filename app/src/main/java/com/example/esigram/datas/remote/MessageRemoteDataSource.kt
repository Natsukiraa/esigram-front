package com.example.esigram.datas.remote

import com.example.esigram.datas.mappers.MessageMapper
import com.example.esigram.domains.models.Message
import com.example.esigram.networks.RetrofitInstance
import com.example.esigram.datas.remote.services.MessageApiService
import com.example.esigram.providers.FirebaseProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Response

class MessageRemoteDataSource {
    private val api = RetrofitInstance.api
    private val messageService = api.create(MessageApiService::class.java)

    // Mettre la logique métier ici
    /*
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
    * */

    fun listenMessage(chatId: String): Flow<List<Message>> {
        // Implementation goes here
        return callbackFlow {
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
    }

    suspend fun createMessage(
        chatId: String,
        content: String,
        files: List<java.io.File>? = null
    ): Response<Unit> {
        // Implementation goes here
        return messageService.sendMessage(chatId)
    }

    suspend fun deleteMessage(messageId: String): Response<Unit> {
        // Implementation goes here
        return messageService.deleteMessage(messageId)
    }

}