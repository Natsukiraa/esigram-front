package com.example.esigram.datas.mappers

import com.example.esigram.datas.remote.models.MediaDto
import com.example.esigram.domains.models.Media

fun MediaDto.toDomain(): Media {
    return Media(
        id = this.id,
        signedUrl = this.signedUrl
    )
}