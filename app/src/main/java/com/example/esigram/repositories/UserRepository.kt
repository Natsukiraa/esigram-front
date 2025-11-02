package com.example.esigram.repositories

import com.example.esigram.services.ApiService
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.File

class UserRepository {
    private val api = ApiService()

    /*
     * GET /me
     */
    suspend fun getMe(): HttpResponse {
        val response = api.client.get("/users/me")
        return response
    }

    /*
     * PATCH /users/me
     * Format : form-data
     * Fiels :
     * - data : { username: xx, description: xx }
     * - profilePicture : truc.png
     */
    suspend fun registerUserToDB(
        username: String,
        description: String?,
        file: File?= null
    ): HttpResponse {
        val jsonData = Json.encodeToString(
            serializer = JsonObject.serializer(),
            value = buildJsonObject {
                put("username", username)
                if (description != null) {
                    put("description", description)
                }
            }
        )

        val response = api.client.patch("/users/me") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "data",
                            jsonData,
                            Headers.build {
                                append(
                                    HttpHeaders.ContentType,
                                    ContentType.Application.Json.toString()
                                )
                            }
                        )

                        if (file != null)
                            append(
                                "profilePicture",
                                file.readBytes(),
                                Headers.build {
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=\"${file.name}\""
                                    )
                                    append(
                                        HttpHeaders.ContentType,
                                        ContentType.Image.Any.toString()
                                    )
                                }
                            )
                    }
                )
            )
        }

        return response
    }
}