package com.example.esigram.domains.usecase.friend

import com.example.esigram.domains.repositories.FriendRepository

class GetFriendRequestsUseCase(private val friendRepository: FriendRepository) {
    suspend operator fun invoke(outbound: Boolean) = friendRepository.getFriendRequests(outbound)
}
