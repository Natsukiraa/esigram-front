package com.example.esigram.domains.repositories

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
    fun getUserIdToken(result: (String?) -> Unit)
}