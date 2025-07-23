package com.example.callaguy_professional.professional.presentation.sharedViewModel

import androidx.lifecycle.ViewModel
import com.example.callaguy_professional.professional.domain.ServiceRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnGoingSharedViewModel : ViewModel() {
    private val _selectedOnGoing = MutableStateFlow<ServiceRequests?>(null)
    val selectedOnGoing = _selectedOnGoing.asStateFlow()

    fun onSelectedOnGoing(serviceRequests: ServiceRequests?) {
        _selectedOnGoing.value = serviceRequests
    }
}