package com.example.catalist.repos

import android.util.Log
import com.example.catalist.api.CatDetailsApiModel
import com.example.catalist.api.CatsApi
import com.example.catalist.db.AppDatabase
import com.example.catalist.db.model.CatDbModel
import com.example.catalist.db.model.asCatDbModel
import com.example.catalist.db.model.asCatDbPhoto
import javax.inject.Inject

public class CatApiRepoImpl @Inject constructor (
    private val catsApi: CatsApi,
    private val catsDb: AppDatabase
    ) : CatRepoInterface
{
    override suspend fun getAllCats(): List<CatDetailsApiModel> = catsApi.getAllCats()

    override suspend fun getCat(breedId: String): CatDetailsApiModel = catsApi.getCatDetails(breedId)

    override suspend fun searchCats(query: String): List<CatDetailsApiModel> = catsApi.searchCats(query)

    suspend fun getCatDetails(catId:String) = catsApi.getCatDetails(catId)

    suspend fun getCatPhotos(catId:String) = catsApi.getCatPhotos(catId)

    suspend fun fetchBreeds(){
        val res:List<CatDbModel> = catsApi.getAllCats().map { it.asCatDbModel() }
        catsDb.catDao().upsertAllBreeds(res)
    }
    suspend fun fetchBreed(catId:String){
        val res = catsApi.getCatDetails(catId).asCatDbModel()
        catsDb.catDao().upsertBreed(res)
    }

    suspend fun fetchPhotosForBreed(catId:String){
        val res=catsApi.getCatPhotos(catId).map { it.asCatDbPhoto() }
        res.forEach { it.catId=catId }
        Log.d("mojtag", "fetchPhotosForBreed: $res")
        catsDb.catDao().upsertAllPhotos(res)
    }
    fun observeAllCats()= catsDb.catDao().observeBreeds()
    fun observeCatPhotos(catId: String) = catsDb.catDao().observeBreedPhotos(catId)

    fun observeCat(catId:String)=catsDb.catDao().observeBreed(catId)

}
