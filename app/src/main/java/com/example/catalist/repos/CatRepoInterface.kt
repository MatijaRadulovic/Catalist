package com.example.catalist.repos

import com.example.catalist.api.CatDetailsApiModel


interface CatRepoInterface {
    suspend fun getAllCats(): List<CatDetailsApiModel>
    suspend fun getCat(breedId: String): CatDetailsApiModel
    suspend fun searchCats(query: String): List<CatDetailsApiModel>

}