package com.example.catalist.profile

import com.example.catalist.profile.storage.ProfileModel

interface ProfileContract {
    data class ProfileDetailsState(
        val loading: Boolean = true,
        val data: ProfileModel = ProfileModel("","",""),
        val error: Throwable? = null,
    )
    sealed class ProfileEvent{
        data class LoginEvent(
            val name:String,
            val username:String,
            val email:String
        ): ProfileEvent()
    }
    sealed class ProfileEffect{
        data object CloseLoginEffect: ProfileEffect()
    }
}