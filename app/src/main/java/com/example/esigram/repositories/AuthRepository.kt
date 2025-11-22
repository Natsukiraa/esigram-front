package com.example.esigram.repositories

import android.net.Uri
import com.example.esigram.services.ApiService
import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val api = ApiService()

    fun getCurrentUser() = auth.currentUser
    fun signOut() = auth.signOut()

    fun getUserIdToken(result: (String?) -> Unit) {
        val user = auth.currentUser

        if (user == null) {
            result(null)
            return
        }

        user.getIdToken(true)
            .addOnSuccessListener {
                result(it.token)
            }
            .addOnFailureListener {
                result(null)
            }
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
        file: Uri? = null
    ): HttpResponse {
        val response = api.client.patch("${api.baseUrl}/users/me") {
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
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "form-data; name=\"profilePicture\"; filename=\"${filePath.name}\""
                                        )
                                        append(
                                            HttpHeaders.ContentType,
                                            ContentType.Image.Any.toString()
                                        )
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