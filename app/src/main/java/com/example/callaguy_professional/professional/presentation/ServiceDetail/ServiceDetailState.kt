package com.example.callaguy_professional.professional.presentation.ServiceDetail

import com.example.callaguy_professional.professional.domain.ServiceRequests

data class ServiceDetailState(
    val service : ServiceRequests? = null,
    val errorMessage : String? = null,
    val isLoading : Boolean = false,
    val isSuccessful : Boolean = false
)
