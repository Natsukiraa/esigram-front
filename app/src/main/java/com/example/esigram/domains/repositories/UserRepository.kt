package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.TmpUser
import com.example.esigram.domains.models.responses.PageModel
import com.example.esigram.domains.models.CorrectUser
import com.example.esigram.domains.models.responses.UserResponse
import retrofit2.Response
import java.io.File

interface UserRepository {
    suspend fun getMe(): Response<UserResponse>
    suspend fun registerUserToDB(username: String, description: String?, file: File?= null): Result<Unit>
    suspend fun getUsers(page: Int, size: Int, username: String?): PageModel<TmpUser>
}