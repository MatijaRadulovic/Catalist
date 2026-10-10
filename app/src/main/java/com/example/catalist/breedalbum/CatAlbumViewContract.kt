package com.example.catalist.breedalbum

import com.example.catalist.db.model.CatDbPhoto

interface CatAlbumViewContract {

    data class AlbumUiState(
        val loading: Boolean=true,
        val catId: String="",
        val data: List<CatDbPhoto> =emptyList<CatDbPhoto>(),
        val error: Exception?=null,
        val catIndex: Int
    )
}