package com.example.catalist.breeddetails

interface CatDetailsViewContract {

    data class CatDetailsState(
        val catId: String="",
        val loading: Boolean= true,
        val data: CatDetailsModel?=null,
        val wikiUrl: String?=null,
        val error: Exception?=null
    )
    sealed class CatDetailsEvent{
        data object ViewWikiEvent: CatDetailsEvent()
    }
    sealed class CatDetailsEffect{
        data object LaunchWikiEffect: CatDetailsEffect()
    }
}