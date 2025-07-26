package com.example.callaguy_professional.professional.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy_professional.R
import com.example.callaguy_professional.core.auth.TokenProvider
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.core.presentation.components.toUiText
import com.example.callaguy_professional.professional.domain.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val tokenProvider: TokenProvider,
    private val repository: ProfileRepository
) : ViewModel(){

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    
    init {
        getProfileInfo()
    }

    fun onAction(action : ProfileAction) {
        when(action) {
            ProfileAction.OnLogOutClick -> {
                onLogOut()
            }
            is ProfileAction.OnProfileImageChanged -> {
                updateProfilePicture(
                    uri = action.image,
                    context= action.context
                )
            }
            ProfileAction.ResetSuccess -> {
                _state.update {
                    it.copy(
                        isSuccess = false
                    )
                }
            }
            ProfileAction.Retry -> {
                getProfileInfo()
            }
        }
    }

    private fun updateProfilePicture( uri : Uri , context: Context) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    profileUpdateError = null
                )
            }
            repository
                .uploadProfilePicture(uri , context)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            profileUpdateError = context.getString(R.string.profile_update_failed_try_again)
                        )
                    }
                }
        }
    }

    private fun getProfileInfo() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isProfileStateLoading = true,
                    profileStateError = null,
                    )
            }
            repository
                .getProfileInfo()
                .onSuccess { result -> 
                    _state.update { 
                        it.copy(
                            info = result,
                            isProfileStateLoading = false
                        )
                    }
                }
                .onError { error -> 
                    _state.update { 
                        it.copy(
                            isProfileStateLoading = false,
                            profileStateError = error.toUiText())
                    }
                }
        }
    }

    private fun onLogOut() {
        tokenProvider.clearToken()
    }


}