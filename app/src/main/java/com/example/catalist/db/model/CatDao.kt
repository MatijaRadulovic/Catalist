package com.example.catalist.db.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Upsert
    fun upsertAllBreeds(data: List<CatDbModel>)

    @Upsert
    fun upsertAllPhotos(data: List<CatDbPhoto>)

    @Upsert
    fun upsertBreed(data: CatDbModel)

    @Query("SELECT * FROM CatDbModel")
    fun observeBreeds(): Flow<List<CatDbModel>>

    @Query("SELECT * FROM CatDbPhoto WHERE CatDbPhoto.catId = :catId")
    fun observeBreedPhotos(catId: String): Flow<List<CatDbPhoto>>

    @Query("SELECT * FROM CatDbModel WHERE CatDbModel.id=:catId")
    fun observeBreed(catId:String):Flow<CatDbModel>
}
