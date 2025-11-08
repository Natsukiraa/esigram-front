package com.example.esigram.domains.models

data class User(
    var id: String,
    var forename: String,
    var name: String,
    var image: String ?= null,
    var isOnline: Boolean = false
)