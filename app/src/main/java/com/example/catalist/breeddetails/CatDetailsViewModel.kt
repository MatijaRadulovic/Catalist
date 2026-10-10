package com.example.catalist.breeddetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import com.example.catalist.breeddetails.CatDetailsViewContract.CatDetailsState
import com.example.catalist.breeddetails.CatDetailsViewContract.CatDetailsEvent
import com.example.catalist.navigation.CAT_ID_ARG
import javax.inject.Inject
import com.example.catalist.breeddetails.CatDetailsViewContract.CatDetailsEffect
import com.example.catalist.repos.CatApiRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel

class CatDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: CatApiRepoImpl
): ViewModel() {
    private val catId = savedStateHandle.get<String>(CAT_ID_ARG) ?: error("Cat id arg not found.")

    private val _state=MutableStateFlow(CatDetailsViewContract.CatDetailsState(catId=catId))
    val state=_state.asStateFlow()
    private fun setState(reducer: CatDetailsState.()-> CatDetailsState) {_state.getAndUpdate(reducer) }

    private val events= MutableSharedFlow<CatDetailsEvent>()
    fun setEvent(event: CatDetailsEvent)= viewModelScope.launch{
        events.emit(event)
    }

    private val _effect: Channel<CatDetailsViewContract.CatDetailsEffect> = Channel()
    val effect = _effect.receiveAsFlow()
    private fun setEffect(effect: CatDetailsViewContract.CatDetailsEffect) = viewModelScope.launch { _effect.send(effect) }


    init {
        observeEvents()
        loadData(catId)
    }

    private fun loadData(catId:String) {
        setState { copy(loading=true) }
        viewModelScope.launch (Dispatchers.IO){
            try {
                val data=repo.getCat(catId).asCatDetailsModel()
                setState { copy(loading=false, data = data, wikiUrl = data.wikipediaUrl ) }
            }catch (error: Exception){
                setState { copy(error = error) }
            }
            finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event->
                when(event){
                    is CatDetailsEvent.ViewWikiEvent ->{
                        setEffect(CatDetailsEffect.LaunchWikiEffect)
                    }
                }
            }
        }
    }
}