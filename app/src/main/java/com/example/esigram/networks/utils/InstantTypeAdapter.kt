package com.example.esigram.networks.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.time.Instant

class InstantTypeAdapter : JsonSerializer<Instant>, JsonDeserializer<Instant> {
    override fun serialize(
        src: Instant?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Instant {
        return try {
            Instant.parse(json?.asString)
        } catch (e: Exception) {
            e.printStackTrace()
            Instant.now()
        }
    }
}