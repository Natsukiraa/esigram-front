package com.example.esigram.domains.repositories

import retrofit2.Response
import java.io.File

interface UserRepository {
    suspend fun getMe(): Response<Unit>
    suspend fun registerUserToDB(username: String, description: String?, file: File?= null): Result<Unit>
}