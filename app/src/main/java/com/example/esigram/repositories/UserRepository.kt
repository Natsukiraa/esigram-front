package com.example.esigram.repositories

import android.net.Uri
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
import java.io.File
import kotlin.io.inputStream

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
        file: Uri?= null
    ): HttpResponse {
        val response = api.client.patch("/users/me") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "data",
                            """
                                        {
                                            "username": "$username",
                                            "description": "${description ?: ""}"
                                        }
                                    """.trimIndent()
                        )

                        file?.let {
                            val filePath = it.path?.let { path -> File(path) }
                            if (filePath != null && filePath.exists()) {
                                val bytes = filePath.inputStream().readBytes()
                                append(
                                    key = "profilePicture",
                                    value = bytes,
                                    headers = Headers.build {
                                        append(HttpHeaders.ContentDisposition, "form-data; name=\"profilePicture\"; filename=\"${filePath.name}\"")
                                        append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                                    }
                                )
                            }
                        }
                    }
                )
            )
        }

        return response
    }
}