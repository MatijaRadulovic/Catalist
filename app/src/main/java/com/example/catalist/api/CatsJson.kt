package com.example.catalist.api

import kotlinx.serialization.json.Json

val CatsJson= Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}