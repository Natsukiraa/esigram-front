package com.example.esigram.domains.usecase.media

import com.example.esigram.domains.repositories.MediaRepository

class GetMediaUseCase(private val mediaRepository: MediaRepository) {
        suspend operator fun invoke(id: String) = mediaRepository.getMedia(id)
}