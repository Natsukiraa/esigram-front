package com.example.esigram.datas.mappers

import com.example.esigram.datas.remote.models.UserDto
import com.example.esigram.domains.models.UserConversation

fun UserDto.toDomain(): UserConversation {
    return UserConversation(
        id = this.id,
        username = this.username,
        profilePicture = this.profilePicture
    )
}