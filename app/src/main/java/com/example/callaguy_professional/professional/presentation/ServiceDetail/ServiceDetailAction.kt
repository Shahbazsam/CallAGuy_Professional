package com.example.callaguy_professional.professional.presentation.ServiceDetail

import com.example.callaguy_professional.professional.domain.ServiceRequests

interface ServiceDetailAction {
    data class OnServiceDetailChange(val service : ServiceRequests) : ServiceDetailAction
    data class OnAcceptJobClick(val serviceId : Int) : ServiceDetailAction
}