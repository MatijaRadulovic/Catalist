package com.example.catalist.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.profile.ProfileContract.ProfileEffect.CloseLoginEffect
import com.example.catalist.profile.storage.ProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.example.catalist.profile.storage.ProfileStore
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileStore: ProfileStore,
) : ViewModel() {
    private val _hasProfile = MutableStateFlow(false)
    val hasProfile = _hasProfile

    private val _bootCompleted = MutableStateFlow(false)
    val bootCompleted = _bootCompleted
    private val events = MutableSharedFlow<ProfileContract.ProfileEvent>()
    fun setEvent(event: ProfileContract.ProfileEvent) = viewModelScope.launch {
        events.emit(event)
    }

    private val _effect: Channel<CloseLoginEffect> = Channel()
    val effect = _effect.receiveAsFlow()
    private fun setEffect(effect: CloseLoginEffect) = viewModelScope.launch { _effect.send(effect) }

    init {
        viewModelScope.launch {
            _hasProfile.emit(profileStore.profile.value != null)
            _bootCompleted.emit(true)
        }
        observeEvents()
    }


    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {

                    is ProfileContract.ProfileEvent.LoginEvent -> {
                        Log.d("mojtag", "observeEvents: radi nest")

                        profileStore.replaceProfile(
                            ProfileModel(
                                event.name,
                                event.username,
                                event.email
                            )
                        )
                        setEffect(CloseLoginEffect)
                    }
                }
            }
        }
    }
}