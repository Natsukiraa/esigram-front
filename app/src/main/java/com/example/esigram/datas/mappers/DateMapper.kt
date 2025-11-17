package com.example.esigram.datas.mappers

import java.time.Instant

fun parseInstant(value: String?): Instant {
    val str = value ?: return Instant.now()
    return try {
        Instant.parse(str)
    } catch (e: Exception) {
        Instant.now()
    }
}