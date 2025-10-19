package com.example.esigram.repositories

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUser() = auth.currentUser
    fun signOut() = auth.signOut()
}