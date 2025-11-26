package com.example.esigram.domains.models

data class OldUser(
    var id: String,
    var forename: String,
    var name: String,
    var image: String? = null,
    var isOnline: Boolean = false
)