package com.example.catalist.breedalbum

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.navigation.CAT_ID_ARG
import com.example.catalist.navigation.CAT_IND_ARG
import com.example.catalist.repos.CatApiRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class CatAlbumViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: CatApiRepoImpl): ViewModel()
{
    private val catId = savedStateHandle.get<String>(CAT_ID_ARG)?:error("Cat id arg not found.")
    private val catIndex = savedStateHandle.get<Int>(CAT_IND_ARG)?:-1

    private val _state= MutableStateFlow(CatAlbumViewContract.AlbumUiState(catId = catId, catIndex = catIndex))
    val state=_state.asStateFlow()
    private fun setState(reducer: CatAlbumViewContract.AlbumUiState.()-> CatAlbumViewContract.AlbumUiState) =
        viewModelScope.launch{
            _state.getAndUpdate(reducer)
        }

    init{
        fetchPhotos(catId)
        observePhotos(catId)
    }

    private fun observePhotos(catId: String) = viewModelScope.launch {
        setState { copy(loading=true) }
        try {
            repo.observeCatPhotos(catId).distinctUntilChanged().collect {
                setState { copy(loading=false,data=it) }
            }
        }catch (error: Exception){
            setState { copy(loading=false,error=error) }
            Log.d("mojtag", "observePhotos: $error")
        }
    }

    private fun fetchPhotos(catId: String) = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                repo.fetchPhotosForBreed(catId)
            }
        }catch (error: Exception){
        }
    }
}