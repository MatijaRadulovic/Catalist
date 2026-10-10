package com.example.catalist.breedlist

interface CatListViewContract {
    data class ListUiState(
        val loading: Boolean = true,
        val data: List<CatListModel> = emptyList(),
        val error: Throwable? = null,
        val query: String? = null
    )

    sealed class ListUiEvent {
        data class SearchData(val query: String?) : ListUiEvent()
    }

}