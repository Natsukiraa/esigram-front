package com.example.esigram.networks

import com.example.esigram.datas.repositories.AuthRepositoryImpl
import com.example.esigram.networks.interceptors.AuthInterceptor
import com.example.esigram.networks.utils.InstantTypeAdapter
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.215:8080/"
    private val authRepository = AuthRepositoryImpl()
    val gson = GsonBuilder()
        .registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
        .create()
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(authRepository))
        .build()

    val api: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}