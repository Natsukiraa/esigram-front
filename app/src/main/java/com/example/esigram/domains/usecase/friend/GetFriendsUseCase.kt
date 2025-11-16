package com.example.esigram.domains.usecase.friend

import com.example.esigram.domains.repositories.FriendRepository

class GetFriendsUseCase(private val friendRepository: FriendRepository) {
    suspend operator fun invoke() = friendRepository.getFriends()
}
