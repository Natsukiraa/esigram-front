package com.example.esigram.datas.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSource {
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUser() = auth.currentUser
    fun signOut() = auth.signOut()

    fun getUserIdToken(result: (String?) -> Unit) {
        val user = auth.currentUser

        if (user == null) {
            result(null)
            return
        }

        user.getIdToken(true)
            .addOnSuccessListener {
                result(it.token)
            }
            .addOnFailureListener {
                result(null)
            }
    }

    suspend fun login(email: String, pass: String): Result<FirebaseUser> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, pass).await()
            val user = authResult.user
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User or password incorrect"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, pass: String): Result<FirebaseUser> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
            val user = authResult.user
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Could not register user. Please try again later."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}