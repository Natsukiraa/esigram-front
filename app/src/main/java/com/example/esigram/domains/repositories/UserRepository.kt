package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.TmpUser
import com.example.esigram.domains.models.responses.PageModel
import retrofit2.Response
import java.io.File

interface UserRepository {
    suspend fun getMe(): Response<Unit>
    suspend fun registerUserToDB(username: String, description: String?, file: File?= null): Result<Unit>
    suspend fun getUsers(page: Int, size: Int, username: String?): PageModel<TmpUser>
}