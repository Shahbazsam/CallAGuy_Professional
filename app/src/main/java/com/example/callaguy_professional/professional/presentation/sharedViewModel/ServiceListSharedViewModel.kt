package com.example.callaguy_professional.professional.presentation.sharedViewModel

import androidx.lifecycle.ViewModel
import com.example.callaguy_professional.professional.domain.ServiceRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ServiceListSharedViewModel : ViewModel() {

    private val _selectedService = MutableStateFlow<ServiceRequests?>(null)
    val selectedService = _selectedService.asStateFlow()

    fun onSelectedService(serviceRequests: ServiceRequests?) {
        _selectedService.value = serviceRequests
    }
}