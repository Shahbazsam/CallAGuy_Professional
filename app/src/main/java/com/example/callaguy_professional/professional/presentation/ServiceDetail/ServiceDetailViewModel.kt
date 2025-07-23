package com.example.callaguy_professional.professional.presentation.ServiceDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.professional.domain.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ServiceDetailViewModel(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ServiceDetailState())
    val state = _state.asStateFlow()


    fun onAction(action: ServiceDetailAction) {
        when (action) {
            is ServiceDetailAction.OnServiceDetailChange -> {
                _state.update {
                    it.copy(
                        service = action.service
                    )
                }
            }

            is ServiceDetailAction.OnAcceptJobClick -> {
                onAcceptJob(
                    serviceId = action.serviceId
                )
            }
        }
    }

    private fun onAcceptJob(serviceId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            repository
                .onAcceptJob(serviceId)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccessful = true
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toString()
                        )
                    }
                }
        }
    }
}