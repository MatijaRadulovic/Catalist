package com.example.catalist.profile.storage

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

class ProfileSerializer : OkioSerializer<ProfileModel?> {

    private val jsonSerializer = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override val defaultValue: ProfileModel?
        get() = null

    override suspend fun readFrom(source: BufferedSource): ProfileModel? {
        return runCatching {
            jsonSerializer.decodeFromString<ProfileModel?>(source.readUtf8())
        }.getOrNull()
    }

    override suspend fun writeTo(
        t: ProfileModel?,
        sink: BufferedSink
    ) {
        sink.use {
            if (t != null) {
                it.writeUtf8(jsonSerializer.encodeToString(ProfileModel.serializer(), t))
            } else {
                it.writeUtf8("")
            }
        }
    }
}