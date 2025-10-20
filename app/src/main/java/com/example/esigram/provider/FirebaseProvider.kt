package com.example.esigram.provider

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase


object FirebaseProvider {
    private var initialized = false

    fun init(context: Context) {
        if (!initialized) {
            FirebaseApp.initializeApp(context)
            initialized = true
        }
    }

    val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
}
