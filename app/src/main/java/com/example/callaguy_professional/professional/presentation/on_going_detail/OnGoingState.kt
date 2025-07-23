package com.example.callaguy_professional.professional.presentation.on_going_detail

import com.example.callaguy_professional.professional.domain.ServiceRequests

data class OnGoingState(
    val service : ServiceRequests? = null,
    val errorMessage : String? = null,
    val isLoading : Boolean = false,
    val isSuccessful : Boolean = false
)
