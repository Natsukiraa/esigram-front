package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.UserRemoteDataSource
import com.example.esigram.domains.repositories.UserRepository
import com.example.esigram.models.UserConversation
import java.io.File

class UserRepositoryImpl(
    val remote: UserRemoteDataSource = UserRemoteDataSource()
) : UserRepository {
    override suspend fun getMe() = remote.getMe()

    override suspend fun patchUser(username: String, description: String?, file: File?) =
        remote.patchUser(username, description, file)

    override suspend fun getUsers(page: Int, size: Int, username: String?) =
        remote.getUsers(page, size, username)

    override suspend fun getUserById(userId: String): UserConversation? {
        return remote.getUserById(userId)
    }

    override suspend fun getOnboardingStatus() = remote.getOnboardingStatus()
    override suspend fun completeOnboarding() = remote.completeOnboarding()
}