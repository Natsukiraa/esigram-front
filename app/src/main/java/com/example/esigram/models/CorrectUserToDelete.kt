package com.example.esigram.models

data class CorrectUserToDelete(
    var id: String,
    var username: String,
    var email: String,
    var description: String ?= null,
    var profilePicture: Media ?= null,
    var hasStories: Boolean = false,
    var alreadyViewedStories: Boolean = false
)
