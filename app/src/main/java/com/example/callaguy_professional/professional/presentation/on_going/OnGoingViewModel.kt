package com.example.callaguy_professional.professional.presentation.on_going

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.domain.OnGoingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnGoingViewModel(
    private val repository : OnGoingRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OnGoingScreenState())
    val state = _state
        .onStart {
            getAcceptedJobs()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    fun getAcceptedJobs() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            repository
                .getAcceptedJobs()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            accepted = result.filter { order ->
                                order.status == ServiceRequestStatus.ACCEPTED
                            },
                            past = result.filter { order ->
                                order.status != ServiceRequestStatus.ACCEPTED
                            },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            accepted = emptyList(),
                            past = emptyList(),
                            errorMessage = error.toString()
                        )
                    }
                }
        }
    }
}