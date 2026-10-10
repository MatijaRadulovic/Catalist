package com.example.catalist.breedlist

import com.example.catalist.api.CatDetailsApiModel
import com.example.catalist.db.model.CatDbModel

data class CatListModel(
    val id: String,
    val name: String,
    val temperament: List<String>,
    val description: String,
    val altNames: String,
    val imageURL: String,
)

public fun CatDetailsApiModel.asCatListModel() : CatListModel {

    val cat=CatListModel(
        id = this.id,
        name = this.name,
        temperament = this.temperament.split(", ")
            .subList(0, minOf(6, this.temperament.count { it == ',' } + 1)),
        description = this.description.substring(0, minOf(250, this.description.length)),
        altNames = this.altNames ?: "",
        imageURL = "https://cdn2.thecatapi.com/images/" + this.imageId+".jpg"
    )
    return cat
}
public fun CatDbModel.asCatListModel() : CatListModel {

    val cat=CatListModel(
        id = this.id,
        name = this.name,
        temperament = this.temperament.split(", ")
            .subList(0, minOf(6, this.temperament.count { it == ',' } + 1)),
        description = this.description.substring(0, minOf(250, this.description.length)),
        altNames = this.altNames,
        imageURL = this.thumbImageURL
    )
    return cat
}