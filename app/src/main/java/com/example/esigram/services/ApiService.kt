package com.example.esigram.services

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
class ApiService {
    private val bearerToken = "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjdlYTA5ZDA1NzI2MmU2M2U2MmZmNzNmMDNlMDRhZDI5ZDg5Zjg5MmEiLCJ0eXAiOiJKV1QifQ.eyJhdXRob3JpdGllcyI6WyJVU0VSIl0sImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9lc2lncmFtLWFwaSIsImF1ZCI6ImVzaWdyYW0tYXBpIiwiYXV0aF90aW1lIjoxNzYxMjUxNzI1LCJ1c2VyX2lkIjoiRXBGUDBpQzc5cFV3c1dtZUlCbGUyT0hRTkF2MiIsInN1YiI6IkVwRlAwaUM3OXBVd3NXbWVJQmxlMk9IUU5BdjIiLCJpYXQiOjE3NjEyNTE3MjUsImV4cCI6MTc2MTI1NTMyNSwiZW1haWwiOiJsZW5hc2VuZGVyQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJsZW5hc2VuZGVyQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.bZbFBh8y6luDBbzpdoScp5731ZncqfsJbLNfqAM12hgnOYTG-rILRiNaJo92pzB-1XYOtlV_V8ZWGP2M7U_Q5ivoMYopTiEoIwOy98vy0UXOpqhkqUjI0BN0oFwKKgux2dG9TFrH02Cz0cUbODPvlRa9roDKpuY2THRYO7jmucbDQbt88A0gu9K-mZ1cWVV44F1XWg8C3_q2YB2B0e8OUYcFXy3RmhG0Lb0sYbQ4MCsKxXPrTxnqpdQXhijijUGnuHQaGU081_WkXHX-VocJV8ImpQuC3iSK3XqBtFjD1dIKrRM1cUw5qn7uj0xdp1TIfiSnMJ5YG5G7Pqso0H52fA"
    val baseUrl = "http://10.0.2.2:8080"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        defaultRequest {
            header(HttpHeaders.Authorization, bearerToken)
            contentType(ContentType.Application.Json)
        }
    }

}
