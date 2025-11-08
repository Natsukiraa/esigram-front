package com.example.esigram.models

import java.time.Instant

data class FriendRequest(
    val id: String? = null,
    val userAsking: CorrectUserToDelete? = null,
    val userAsked: CorrectUserToDelete? = null,
    val status: FriendStatus? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
) {
    enum class FriendStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
