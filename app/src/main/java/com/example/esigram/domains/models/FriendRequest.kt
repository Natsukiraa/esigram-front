package com.example.esigram.domains.models

data class FriendRequest(
    val id: String? = null,
    val userAsking: TmpUser? = null,
    val userAsked: TmpUser? = null,
    val status: FriendStatus? = null,
    val createdAt: String? = null,
) {
    enum class FriendStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}