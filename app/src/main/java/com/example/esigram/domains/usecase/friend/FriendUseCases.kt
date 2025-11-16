package com.example.esigram.domains.usecase.friend

data class FriendUseCases(
    val askFriendUseCase: AskFriendUseCase,
    val rejectFriendUseCase: RejectFriendUseCase,
    val removeFriendUseCase: RemoveFriendUseCase,
    val getFriendsUseCase: GetFriendsUseCase,
    val getFriendRequestsUseCase: GetFriendRequestsUseCase
)