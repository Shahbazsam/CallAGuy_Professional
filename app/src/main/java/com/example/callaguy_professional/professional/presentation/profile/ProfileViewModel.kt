package com.example.callaguy_professional.professional.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.callaguy_professional.core.auth.TokenProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val tokenProvider: TokenProvider
) : ViewModel(){

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun onAction(action : ProfileAction) {
        when(action) {
            ProfileAction.OnLogOutClick -> TODO()
            is ProfileAction.OnProfileImageChanged -> {
                updateProfilePicture(
                    uri = action.image,
                    context= action.context
                )
            }
        }
    }

    private fun updateProfilePicture( uri : Uri , context: Context) {

    }

    private fun getProfileInfo() {

    }


}