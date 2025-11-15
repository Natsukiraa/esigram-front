package com.example.esigram.domains.usecase.friend

import com.example.esigram.domains.repositories.FriendRepository

class RejectFriendUseCase(private val friendRepository: FriendRepository) {
    suspend operator fun invoke(friendId: String) = friendRepository.rejectFriend(friendId)
}
