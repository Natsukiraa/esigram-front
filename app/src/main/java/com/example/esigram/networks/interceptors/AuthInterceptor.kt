package com.example.esigram.networks.interceptors

import com.example.esigram.domains.repositories.AuthRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authRepository: AuthRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            suspendCancellableCoroutine {
                authRepository.getUserIdToken { token ->
                    it.resume(token) { _, _, _ -> }
                }
            }
        }

        val currentRequest = chain.request().newBuilder()
        currentRequest.addHeader("Authorization", "Bearer $token")

        val newRequest = currentRequest.build()
        return chain.proceed(newRequest)
    }
}