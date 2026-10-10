package com.example.catalist.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatsApi {
    @GET("/v1/breeds")
    suspend fun getAllCats(): List<CatDetailsApiModel>

    @GET("/v1/breeds/{breed_id}")
    suspend fun getCatDetails(@Path("breed_id") breedId: String): CatDetailsApiModel

    @GET("/v1/breeds/search")
    suspend fun searchCats(@Query("q") query: String): List<CatDetailsApiModel>

    @GET("/v1/images/search?format=json&limit=30&has_breeds=1")
    suspend fun getCatPhotos(@Query("breed_ids") breedId: String):List<CatPhotoApiModel>

}
