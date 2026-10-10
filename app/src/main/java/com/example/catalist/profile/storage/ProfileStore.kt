package com.example.catalist.profile.storage

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileStore @Inject constructor(
    private val persistence: DataStore<ProfileModel?>,
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    val profile = persistence.data
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = runBlocking { persistence.data.first() },
        )

    suspend fun replaceProfile(profileModel: ProfileModel) {
        persistence.updateData { existing ->
            profileModel
        }
    }
}