package com.example.callaguy_professional.professional.presentation.service_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.core.presentation.components.toUiText
import com.example.callaguy_professional.professional.domain.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServiceRequestViewModel(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ServiceListState())
    val state = _state
        .onStart {
            getServiceRequests()
        }
        .stateIn(
            scope = viewModelScope,
            started =  SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )


    private fun getServiceRequests(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            repository
                .getServiceList()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            serviceList = result
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            serviceList = emptyList(),
                            errorMessage = error.toString()
                        )
                    }
                }

        }
    }
}