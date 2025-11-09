package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.UserRemoteDataSource
import com.example.esigram.domains.repositories.UserRepository
import java.io.File

class UserRepositoryImpl(
    val remote: UserRemoteDataSource = UserRemoteDataSource()
): UserRepository {
    override suspend fun getMe() = remote.getMe()
    override suspend fun registerUserToDB(username: String, description: String?, file: File?) = remote.registerUserToDB(username, description, file)
}