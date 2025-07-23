package com.example.callaguy_professional.professional.presentation.on_going_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.domain.OnGoingRepository
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailAction
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnGoingDetailViewModel(
    private val repository: OnGoingRepository
) : ViewModel(){
    private val _state = MutableStateFlow(OnGoingState())
    val state = _state.asStateFlow()


    fun onAction(action: OnGoingDetailAction) {
        when (action) {
            is OnGoingDetailAction.OnGoingDetailChange -> {
                _state.update {
                    it.copy(
                        service = action.service
                    )
                }
            }

            is OnGoingDetailAction.OnUpdateStatusClick -> {
                onUpdateStatus(action.serviceId)
            }
        }
    }

    private fun onUpdateStatus(serviceId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            repository
                .updateJobStatus(
                    serviceId = serviceId,
                    status = ServiceRequestStatus.COMPLETED
                )
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccessful = true,
                            errorMessage = null
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