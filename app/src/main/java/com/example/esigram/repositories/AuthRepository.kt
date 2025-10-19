package com.example.esigram.repositories

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUser() = auth.currentUser
    fun signOut() = auth.signOut()

    fun getUserIdToken(result: (String?) -> Unit) {
        val user = auth.currentUser

        if(user == null) {
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
}