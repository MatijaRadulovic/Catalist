package com.example.catalist.breedlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import  com.example.catalist.breedlist.CatListViewContract.ListUiState
import  com.example.catalist.breedlist.CatListViewContract.ListUiEvent
import com.example.catalist.api.CatDetailsApiModel
import com.example.catalist.repos.CatApiRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.withContext

@HiltViewModel
class CatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val catRepoInterface: CatApiRepoImpl
):ViewModel() {

private val _state = MutableStateFlow(ListUiState())
val state = _state.asStateFlow()
private fun setState(reducer: ListUiState.() -> ListUiState) = _state.getAndUpdate(reducer)

private val events = MutableSharedFlow<ListUiEvent>()
fun setEvent(event: ListUiEvent) = viewModelScope.launch { events.emit(event) }

init {
    fetchBreeds()
    observeEvents()
    observeBreeds()
}

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                Log.d("mojtag", "observeEvents: ${event}")
                when (event) {
                   is ListUiEvent.SearchData -> {loadBreeds(event.query)}
                }
            }
        }
    }

    private fun fetchBreeds():Unit { viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                catRepoInterface.fetchBreeds()
            }
        }catch (error: Exception){}

        }
    }

    private fun observeBreeds()=viewModelScope.launch {
        setState { copy(loading=true) }
        try {
            catRepoInterface.observeAllCats().distinctUntilChanged()
                .collect {
                    setState { copy(loading=false,data=it.map { it.asCatListModel() }) }
                }
        }catch (error: Exception){
            setState { copy(loading=false, error=error) }
        }

    }


    private fun loadBreeds(searchQuery: String?=null)= viewModelScope.launch(Dispatchers.IO) {
        setState { copy(loading = true, query=searchQuery)}
        try {
            val res: List<CatDetailsApiModel> = if(searchQuery!=null && searchQuery.isNotEmpty()){
                catRepoInterface.searchCats(query = searchQuery)
            } else{
                catRepoInterface.getAllCats()
            }
            setState { copy(loading=false, data=res.map { it.asCatListModel() } ) }
        } catch (error: Exception) {
            setState { copy(error = error ) }
            Log.d("mojtag", "loadBreeds: $error")
        }
        finally {
            setState { copy(loading=false) }
        }
        }
    }

