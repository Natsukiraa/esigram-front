package com.example.esigram.repositories

import retrofit2.Response

interface UserRepository {
    suspend fun getMe(): Response<Unit>
    suspend fun registerUserToDB(username: String, description: String?, file: java.io.File?= null): Result<Unit>
}