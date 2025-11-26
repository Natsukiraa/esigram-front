package com.example.esigram.models

import com.example.esigram.domains.models.Media

data class UserConversation(
    val id: String,
    val username: String,
    var profilePicture: Media?= null,
    )