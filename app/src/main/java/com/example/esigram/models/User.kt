package com.example.esigram.models

data class User(
    var id: String,
    var forename: String,
    var name: String,
    var image: String ?= null,
    var isOnline: Boolean = false
)