package com.example.esigram.domains.models

data class User(
    var id: String,
    var username: String,
    var email: String,
    var description: String? = null,
    var profilePicture: Media? = null,
    var hasStories: Boolean = false,
    var alreadyViewedStories: Boolean = false
)
