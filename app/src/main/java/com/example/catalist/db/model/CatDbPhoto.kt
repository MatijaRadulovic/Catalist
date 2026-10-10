package com.example.catalist.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.catalist.api.CatPhotoApiModel

@Entity
data class CatDbPhoto(
    @PrimaryKey(autoGenerate = true) val photoId: Long=0L,
    var catId: String="",
    val imgUrl: String,
    val width: Int,
    val height: Int,
) {
}

public fun CatPhotoApiModel.asCatDbPhoto()= CatDbPhoto(
    imgUrl = this.url,
    width = width,
    height = height
)
