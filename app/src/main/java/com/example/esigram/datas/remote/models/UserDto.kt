package com.example.esigram.datas.remote.models

import com.example.esigram.models.Media

data class ResponsUserDto(
    val data: UserDto
)

data class UserDto (
    val id: String,
    val username: String,
    val profilePicture: Media,
)