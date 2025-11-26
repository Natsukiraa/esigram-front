package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.FriendRequest
import com.example.esigram.domains.models.User
import com.example.esigram.domains.models.responses.PageModel

interface FriendRepository {
    suspend fun askFriend(userId: String)
    suspend fun rejectFriend(userId: String)
    suspend fun removeFriend(userId: String)
    suspend fun getFriends(): PageModel<User>
    suspend fun getFriendRequests(outbound: Boolean): PageModel<FriendRequest>
}