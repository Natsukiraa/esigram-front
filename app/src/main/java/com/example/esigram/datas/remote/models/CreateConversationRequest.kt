package com.example.esigram.datas.remote.models

data class CreateConversationRequest(
    val data: CreateConversation
)

data class CreateConversation(
    val memberIds: List<String>
)