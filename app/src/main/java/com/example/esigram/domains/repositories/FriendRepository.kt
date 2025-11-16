package com.example.esigram.domains.repositories

import com.example.esigram.domains.models.FriendRequest
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.domains.models.responses.PageModel

interface FriendRepository {
    suspend fun askFriend(userId: String)
    suspend fun rejectFriend(userId: String)
    suspend fun removeFriend(userId: String)
    suspend fun getFriends(): PageModel<TmpUser>
    suspend fun getFriendRequests(outbound: Boolean): PageModel<FriendRequest>
}