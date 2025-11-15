package com.example.esigram.datas.remote.services

import com.example.esigram.domains.models.FriendRequest
import com.example.esigram.domains.models.TmpUser
import com.example.esigram.domains.models.responses.PageModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendApiService {
    @POST("/users/{userId}/ask-friend")
    suspend fun askFriend(@Path("userId") userId: String)

    @POST("/users/{friendId}/reject-friend")
    suspend fun rejectFriend(@Path("friendId") friendId: String)

    @POST("/users/{friendId}/remove-friend")
    suspend fun removeFriend(@Path("friendId")friendId: String)

    @GET("/users/me/friends")
    suspend fun getFriends(): PageModel<TmpUser>

    @GET("/users/me/friend-requests")
    suspend fun getFriendRequests(@Query("outbound") outbound: Boolean): PageModel<FriendRequest>
}