package com.example.catalist.profile

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
import com.example.catalist.profile.storage.ProfileStore

//@HiltViewModel
class ProfileDetailsViewModel  constructor(
    private val profileStore: ProfileStore,
    var name: String,
    var username: String,
    var email:String
):ViewModel() {

    init {
        viewModelScope.launch {
            name= profileStore.profile.value?.name ?:""
            email = profileStore.profile.value?.email ?:""
            username= profileStore.profile.value?.username ?: ""
    }
}}

