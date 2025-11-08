package com.example.esigram.datas.remote

import android.util.Log
import com.example.esigram.networks.RetrofitInstance
import com.example.esigram.datas.remote.services.UserApiService
import com.example.esigram.domains.models.CorrectUser
import com.example.esigram.domains.models.UserResponse
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
    private val authService = api.create(UserApiService::class.java)

    suspend fun getMe(): Response<UserResponse> {
        return authService.getMe()
    }

    suspend fun registerUserToDB(
        username: String,
        description: String?,
        file: File?= null): Result<Unit> {
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

            val res = authService.registerUserToDB(
                requestBody,
                profilePicture
            )

            if(res.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error ${res.code()}"))
        } catch (e: Exception){
            Result.failure(e)
        }


    }
}