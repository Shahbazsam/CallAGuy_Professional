package com.example.callaguy_professional.professional.presentation.service_list

import com.example.callaguy_professional.core.presentation.components.UiText
import com.example.callaguy_professional.professional.domain.ServiceRequests

data class ServiceListState(
    val serviceList : List<ServiceRequests> = emptyList(),
    val isLoading : Boolean = true ,
    val errorMessage : String? = null
)
