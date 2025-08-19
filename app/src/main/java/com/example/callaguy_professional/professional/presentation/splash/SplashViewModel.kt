package com.example.callaguy_professional.professional.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy_professional.core.auth.SharedPrefTokenProvider
import com.example.callaguy_professional.core.auth.TokenProvider
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.professional.domain.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val tokenProvider: TokenProvider,
    private val repository: AuthRepository
) : ViewModel() {


    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    fun onAction(action: SplashAction) {
        when (action) {
            SplashAction.CheckApproval -> checkApproval()
        }
    }

    private fun checkApproval() {
        viewModelScope.launch {
            delay(1000L)
            if (tokenProvider.getToken() == null) {
                _state.update {
                    it.copy(
                        loggedBefore = false
                    )
                }
            } else {
                repository
                    .isApproved()
                    .onSuccess { result ->
                        _state.update {
                            it.copy(
                                isApproved = result,
                                loggedBefore = true
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isApproved = false,
                                errorMessage = error.toString()
                            )
                        }
                    }
            }
        }
    }
}