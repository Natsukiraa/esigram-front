package com.example.esigram.domains.models

data class UserConversation(
    val id: String,
    val username: String,
    var profilePicture: Media? = null,
)