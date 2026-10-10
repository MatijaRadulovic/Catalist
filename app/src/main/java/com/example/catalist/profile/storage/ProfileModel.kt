package com.example.catalist.profile.storage

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    val name: String,
    val username: String,
    val email: String,
)
