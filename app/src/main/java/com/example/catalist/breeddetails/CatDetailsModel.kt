package com.example.catalist.breeddetails

import com.example.catalist.api.CatDetailsApiModel

data class CatDetailsModel(
    val id: String,
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
    val imageURL: String,
    val weightFrom: Int,
    val weightTo: Int
)

public fun CatDetailsModel.formatInfo():List<String>{
    val res=mutableListOf<String>()
    res+="Ime: $name"
    res+="Poreklo: $origin"
    res+="Tezina: od $weightFrom do $weightTo"
    res+="Temperament: $temperament"
    return res

}
public fun CatDetailsModel.formatTraits(): List<String>{
    val res=mutableListOf<String>()
    res+="Afekcija: $affectionLevel"
    res+="Odrzvanje: $grooming"
    res+="Zdravlje: $healthIssues"
    res+="Ponasanje: $intelligence"
    res+="Dobrocudnost: $childFriendly"
    return res
}

public fun CatDetailsApiModel.asCatDetailsModel() = CatDetailsModel(
    id = this.id,
    name = this.name,
    temperament = this.temperament,
    description = this.description,
    altNames = this.altNames?:"",
    imageURL = ("https://cdn2.thecatapi.com/images/" + this.imageId+".jpg"),
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