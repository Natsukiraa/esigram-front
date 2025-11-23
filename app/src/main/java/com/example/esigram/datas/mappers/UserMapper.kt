package com.example.esigram.datas.mappers

import androidx.compose.ui.graphics.vector.EmptyPath
import com.example.esigram.datas.remote.models.ConversationDto
import com.example.esigram.datas.remote.models.MessageDto
import com.example.esigram.datas.remote.models.UserDto
import com.example.esigram.domains.models.Conversation
import com.example.esigram.domains.models.Message
import java.time.Instant
import com.example.esigram.domains.models.User
import com.example.esigram.models.CorrectUserToDelete
import com.example.esigram.models.Media
import com.example.esigram.models.UserConversation
import kotlin.String

fun UserDto.toDomain(): UserConversation {
    return UserConversation(
        id = this.id,
        username = this.username,
        profilePicture = this.profilePicture
    )
}