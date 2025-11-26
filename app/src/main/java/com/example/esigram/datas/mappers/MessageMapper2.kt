package com.example.esigram.datas.mappers

import com.example.esigram.datas.remote.models.MessageDto
import com.example.esigram.domains.models.Message
import java.time.Instant


fun MessageDto.toDomain(): Message {
    return Message(
        id = this.id,
        content = this.data["content"] as? String ?: "",
        attachments = this.data["attachments"] as? List<String> ?: emptyList(),
        authorId = this.data["authorId"] as? String ?: "",
        createdAt = this.data["createdAt"] as? Instant ?: Instant.now()
    )
}