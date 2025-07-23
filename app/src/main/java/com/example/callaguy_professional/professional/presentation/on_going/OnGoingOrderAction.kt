package com.example.callaguy_professional.professional.presentation.on_going

import com.example.callaguy_professional.professional.domain.ServiceRequests

interface OnGoingOrderAction {
    data class OnOrderClick(val order: ServiceRequests) : OnGoingOrderAction
}