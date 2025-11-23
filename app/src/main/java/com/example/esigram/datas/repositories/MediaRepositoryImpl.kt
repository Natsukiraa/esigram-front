package com.example.esigram.datas.repositories

import com.example.esigram.datas.remote.MediaRemoteDataSource
import com.example.esigram.domains.repositories.MediaRepository
import retrofit2.Response

class MediaRepositoryImpl(
    val remote: MediaRemoteDataSource = MediaRemoteDataSource()
) : MediaRepository {
    override suspend fun getMedia(id: String) = remote.getMedia(id)
}