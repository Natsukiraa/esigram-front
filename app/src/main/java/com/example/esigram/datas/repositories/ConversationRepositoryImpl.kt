package com.example.esigram.datas.repositories

import android.util.Log
import com.example.esigram.datas.mappers.toDomain
import com.example.esigram.datas.mappers.toDomainBasic
import com.example.esigram.datas.remote.ConversationRemoteDataSource
import com.example.esigram.datas.remote.MediaRemoteDataSource
import com.example.esigram.datas.remote.UserRemoteDataSource
import com.example.esigram.domains.models.Conversation
import com.example.esigram.domains.repositories.ConversationRepository
import com.example.esigram.models.UserConversation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConversationRepositoryImpl(
    val remote: ConversationRemoteDataSource = ConversationRemoteDataSource(),
    val userRemote: UserRemoteDataSource = UserRemoteDataSource(),
    val mediaRemote: MediaRemoteDataSource = MediaRemoteDataSource(),
): ConversationRepository {
    override suspend fun getAll(userId: String) = remote.getAll(userId)
    override suspend fun getById(id: String): Conversation? {
        val conversationData = remote.getById(id)
        val conversation = conversationData?.toDomain()
        return conversation
    }
    override fun observeConversation(id: String): Flow<Conversation?> {
        return remote.observeConversation(id).map { dto ->
            if (dto == null) return@map null

            val basic = dto.toDomainBasic()

            val membersWithMedia = basic.members.map { member ->
                val newMember = userRemote.getUserById(member.id)
                newMember ?: UserConversation(
                    id = member.id,
                    username = member.username,
                    profilePicture = null
                )
            }

            return@map Conversation(
                id = basic.id,
                members = membersWithMedia,
                coverImageId = basic.coverImageId,
                lastMessage = basic.lastMessage,
                unreadCount = basic.unreadCount,
                title = basic.title,
                createdAt = basic.createdAt
            )
        }
    }

    override suspend fun createConversation(ids: List<String>) {
        remote.createConversation(ids)
    }
}