package com.example.esigram.datas.mappers

import ConversationBasic
import com.example.esigram.datas.remote.models.ConversationDto
import com.example.esigram.datas.remote.models.UserConversationBasic
import com.example.esigram.domains.models.Conversation
import com.example.esigram.domains.models.Message
import com.example.esigram.models.UserConversation

fun ConversationDto.toDomain(): Conversation {
    val map = data as Map<String, Any?>

    val membersMap = map["members"] as? Map<String, Map<String, Any?>> ?: emptyMap()

    val members = membersMap.map { (uid, userMap) ->
        UserConversation(
            id = uid,
            username = userMap["username"] as? String ?: ""
        )
    }

    val lastMessageMap = map["lastMessage"] as? Map<String, Any?>

    val lastMessage = lastMessageMap?.let { msg ->
        Message(
            id = msg["id"] as? String ?: "",
            authorId = msg["authorId"] as String,
            content = msg["content"] as String,
            createdAt = parseInstant(msg["createdAt"] as String?)
        )
    }

    return Conversation(
        id = id,
        members = members,
        coverImageId = map["coverImageId"] as? String,
        lastMessage = lastMessage,
        unreadCount = (map["unreadCount"] as? Long)?.toInt() ?: 0,
        title = map["title"] as? String,
        createdAt = parseInstant(data["createdAt"] as String?)
    )
}

fun ConversationDto.toDomainBasic(): ConversationBasic {
    val map = data as Map<String, Any?>

    val membersMap = map["members"] as? Map<String, Map<String, Any?>> ?: emptyMap()

    val members = membersMap.map { (uid, userMap) ->
        UserConversationBasic(
            id = uid,
            username = userMap["username"] as? String ?: "",
            profilePictureId = userMap["profilePicture"] as? String
        )
    }

    val lastMessageMap = map["lastMessage"] as? Map<String, Any?>

    val lastMessage = lastMessageMap?.let { msg ->
        Message(
            id = msg["id"] as? String ?: "",
            authorId = msg["authorId"] as String,
            content = msg["content"] as String,
            createdAt = parseInstant(msg["createdAt"] as String?)
        )
    }

    return ConversationBasic(
        id = id,
        members = members,
        coverImageId = map["coverImageId"] as? String,
        lastMessage = lastMessage,
        unreadCount = (map["unreadCount"] as? Long)?.toInt() ?: 0,
        title = map["title"] as? String,
        createdAt = parseInstant(data["createdAt"] as String?)
    )
}