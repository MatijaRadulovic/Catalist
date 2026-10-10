package com.example.catalist.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatDetailsApiModel(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    @SerialName("alt_names") val altNames: String?=null,
    @SerialName("affection_level") val affectionLevel: Int,
    @SerialName("child_friendly") val childFriendly: Int,
    val grooming: Int,
    @SerialName("health_issues") val healthIssues: Int,
    val intelligence: Int,
    @SerialName("wikipedia_url")val wikipediaUrl: String?=null,
    @SerialName("reference_image_id") val imageId: String?=null,
    val weight: Weight
)
@Serializable
data class Weight(val imperial: String, val metric: String);

@Serializable
data class CatPhotoApiModel(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    //val catId: String
)

// {
//    "weight": {
//      "imperial": "8 - 15",
//      "metric": "4 - 7"
//    },
//    "id": "asho",
//    "name": "American Shorthair",
//    "temperament": "Active, Curious, Easy Going, Playful, Calm",
//    "origin": "United States",
//    "description": "The American Shorthair is known for its longevity, robust health, good looks, sweet personality, and amiability with children, dogs, and other pets.",
//    "alt_names": "Domestic Shorthair",
//    "affection_level": 5,
//    "child_friendly": 4,
//    "grooming": 1,
//    "health_issues": 3,
//    "intelligence": 3,
//    "wikipedia_url": "https://en.wikipedia.org/wiki/American_Shorthair",
//    "reference_image_id": "JFPROfGtQ",


//ime rase,
//alternativna imena rase ukoliko postoje,
//opis rase (maksimalno 250 karaktera opisa; skratiti ukoliko je opis duži),
//najviše pet osobina temperamenta (možete koristiti chips za prikaz).

//slika
//zemlje
//zivotni vek
//prosecna tezina i visina
//5ponasanja
//retka vrsta
//wikiurl
