package com.example.esigram.datas.remote

import android.util.Log
import com.example.esigram.datas.mappers.toDomain
import com.example.esigram.datas.remote.services.UserApiService
import com.example.esigram.domains.models.User
import com.example.esigram.domains.models.responses.OnboardingStatus
import com.example.esigram.domains.models.responses.UserResponse
import com.example.esigram.models.UserConversation
import com.example.esigram.networks.RetrofitInstance
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class UserRemoteDataSource {
    private val api = RetrofitInstance.api
    private val userService = api.create(UserApiService::class.java)

    suspend fun getMe(): Response<UserResponse> {
        return userService.getMe()
    }

    suspend fun getUserById(userId: String): UserConversation? {

        val response = userService.getUserById(userId)

        return if (response.isSuccessful) {
            response.body()?.data?.toDomain()
        } else null
    }

    suspend fun getUserByIdReal(userId: String): User? {
        val response = userService.getUserByIdReal(userId)
        return if (response.isSuccessful) {
            response.body()?.data
        } else null
    }

    suspend fun patchUser(
        username: String,
        description: String?,
        file: File? = null
    ): Result<UserResponse> {
        return try {

            val jsonData = Json.encodeToString(
                serializer = JsonObject.serializer(),
                value = buildJsonObject {
                    put("username", username)
                    description?.let { put("description", description) }
                }
            )

            val requestBody = jsonData.toRequestBody("application/json".toMediaType())

            val profilePicture = file?.let {
                val fileReq = file.asRequestBody("image/*".toMediaType())
                MultipartBody.Part.createFormData(
                    "profilePicture",
                    file.name,
                    fileReq
                )
            }

            val res = userService.patchUser(
                requestBody,
                profilePicture
            )

            if (res.isSuccessful) {
                val meRes = userService.getMe()
                if (meRes.isSuccessful) Result.success(meRes.body()!!)
                else Result.failure(Exception("Error ${meRes.code()}"))
            } else {
                Result.failure(Exception("Error ${res.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }


    }

    suspend fun getUsers(page: Int, size: Int, username: String?) =
        userService.getUsers(page, size, username)

    suspend fun getOnboardingStatus(): Response<OnboardingStatus> =
        userService.getOnboardingStatus()

    suspend fun completeOnboarding(): Response<OnboardingStatus> =
        userService.completeOnboarding()
}