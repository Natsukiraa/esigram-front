package com.example.esigram.domains.repositories


import com.example.esigram.domains.models.TmpUser
import com.example.esigram.domains.models.responses.PageModel
import com.example.esigram.domains.models.responses.UserResponse
import com.example.esigram.models.UserConversation
import retrofit2.Response
import java.io.File

interface UserRepository {
    suspend fun getMe(): Response<UserResponse>
    suspend fun patchUser(
        username: String,
        description: String?,
        file: File? = null
    ): Result<UserResponse>

    suspend fun getUsers(page: Int, size: Int, username: String?): PageModel<TmpUser>
    suspend fun getUserById(userId: String): UserConversation?
}