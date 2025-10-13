package com.example.esigram.models

import java.util.UUID

data class User(
    var id: UUID = UUID.randomUUID(),
    var forename: String,
    var name: String,
    var image: String ?= null,
    var isOnline: Boolean = false
)