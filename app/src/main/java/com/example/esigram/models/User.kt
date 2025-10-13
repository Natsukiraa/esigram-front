package com.example.esigram.models

data class User(
    val id: Int,
    val pseudo: String,
    val avatarUrl: String? = null
)
