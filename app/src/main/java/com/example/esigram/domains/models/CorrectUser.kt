package com.example.esigram.domains.models

import com.google.gson.annotations.SerializedName

data class CorrectUser(
    var id: String,
    var username: String,
    var email: String,
    var description: String? = null,
    @SerializedName("profilePicture") var profilePictureUrl: Media? = null,
    var hasStories: Boolean = false,
    var alreadyViewedStories: Boolean = false
)