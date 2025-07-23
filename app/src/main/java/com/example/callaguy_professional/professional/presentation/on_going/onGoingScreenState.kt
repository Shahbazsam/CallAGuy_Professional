package com.example.callaguy_professional.professional.presentation.on_going

import com.example.callaguy_professional.professional.domain.ServiceRequests

data class OnGoingScreenState(
    val accepted : List<ServiceRequests> = emptyList(),
    val past : List<ServiceRequests> = emptyList(),
    val isLoading : Boolean = true,
    val errorMessage : String ? = null
)
