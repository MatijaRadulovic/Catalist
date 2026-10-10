package com.example.catalist.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.catalist.api.CatDetailsApiModel

@Entity
data class CatDbModel(
    @PrimaryKey val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val altNames: String,
    val affectionLevel: Int,
    val childFriendly: Int,
    val grooming: Int,
    val healthIssues: Int,
    val intelligence: Int,
    val wikipediaUrl: String,
    val thumbImageURL: String,
    val weightFrom: Int,
    val weightTo: Int,

) {
}


public fun CatDetailsApiModel.asCatDbModel() = CatDbModel(
    id = this.id,
    name = this.name,
    temperament = this.temperament,
    description = this.description,
    altNames = this.altNames?:"",
    thumbImageURL = ("https://cdn2.thecatapi.com/images/" + this.imageId+".jpg"),
    origin = this.origin,
    affectionLevel = this.affectionLevel,
    childFriendly = this.childFriendly,
    grooming = this.grooming,
    healthIssues = this.healthIssues,
    intelligence = this.intelligence,
    wikipediaUrl = this.wikipediaUrl?:"",
    weightFrom = this.weight.metric.split("-")[0].trim().toInt(),
    weightTo = this.weight.metric.split("-")[1].trim().toInt()

)