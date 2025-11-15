package com.example.esigram.datas.remote

import com.example.esigram.datas.remote.services.FriendApiService
import com.example.esigram.domains.models.FriendRequest
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.domains.models.responses.PageModel
import com.example.esigram.networks.RetrofitInstance

class FriendRemoteDataSource {
    private val api = RetrofitInstance.api
    private val friendService = api.create(FriendApiService::class.java)

    suspend fun getFriends(): PageModel<TmpUser> = friendService.getFriends()
    suspend fun getFriendRequests(outbound: Boolean): PageModel<FriendRequest> = friendService.getFriendRequests(outbound)
    suspend fun askFriend(userId: String)= friendService.askFriend(userId)
    suspend fun rejectFriend(friendId: String) = friendService.rejectFriend(friendId)
    suspend fun removeFriend(friendId: String) = friendService.removeFriend(friendId)
}