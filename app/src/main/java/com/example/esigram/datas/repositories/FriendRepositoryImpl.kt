package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.FriendRemoteDataSource
import com.example.esigram.domains.repositories.FriendRepository

class FriendRepositoryImpl(
    val remote: FriendRemoteDataSource = FriendRemoteDataSource()
) : FriendRepository {
    override suspend fun getFriends() = remote.getFriends()
    override suspend fun getFriendRequests(outbound: Boolean) = remote.getFriendRequests(outbound)
    override suspend fun askFriend(userId: String) = remote.askFriend(userId)
    override suspend fun rejectFriend(userId: String) = remote.rejectFriend(userId)
    override suspend fun removeFriend(userId: String) = remote.removeFriend(userId)
}