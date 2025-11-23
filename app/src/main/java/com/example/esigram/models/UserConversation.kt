package com.example.esigram.models

import java.time.Instant

data class UserConversation(
    val id: String,
    val username: String,
    var profilePicture: Media ?= null,
    )