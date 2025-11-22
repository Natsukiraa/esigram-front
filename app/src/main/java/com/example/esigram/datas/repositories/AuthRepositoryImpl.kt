package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.AuthRemoteDataSource
import com.example.esigram.domains.repositories.AuthRepository

class AuthRepositoryImpl(
    val remote: AuthRemoteDataSource = AuthRemoteDataSource()
) : AuthRepository {
    override fun getCurrentUser() = remote.getCurrentUser()
    override fun signOut() = remote.signOut()
    override suspend fun login(email: String, pass: String) = remote.login(email, pass)
    override suspend fun register(email: String, pass: String) = remote.register(email, pass)
    override fun getUserIdToken(result: (String?) -> Unit) {
        remote.getUserIdToken(result)
    }
}