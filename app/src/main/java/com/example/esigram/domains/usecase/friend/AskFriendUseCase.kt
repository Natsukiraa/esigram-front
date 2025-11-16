package com.example.esigram.domains.usecase.friend

import com.example.esigram.domains.repositories.FriendRepository

class AskFriendUseCase(private val friendRepository: FriendRepository) {
    suspend operator fun invoke(friendId: String) = friendRepository.askFriend(friendId)
}
