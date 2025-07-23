package com.example.callaguy_professional.professional.presentation.service_list

import com.example.callaguy_professional.professional.domain.ServiceRequests

interface ServiceListAction {
    data class OnServiceRequestClick(val serviceRequest: ServiceRequests) : ServiceListAction
}