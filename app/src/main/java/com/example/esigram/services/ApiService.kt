package com.example.esigram.services

import com.example.esigram.repositories.AuthRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class ApiService {
    private val repository = AuthRepository()
    private val baseUrl = "http://10.0.2.2:8080/"

    private val authPlugin = createClientPlugin("FirebaseAuthPlugin") {
        onRequest { request, _ ->
            val token = suspendCancellableCoroutine<String?> { cont ->
                repository.getUserIdToken { t -> cont.resume(t) { _, _, _ -> } }
            }
            if (token != null) {
                request.headers.append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
        install(Logging) { level = LogLevel.ALL }
        install(authPlugin)
        defaultRequest {
            contentType(ContentType.Application.Json)
            url(baseUrl)
        }
    }
}
