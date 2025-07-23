package com.example.callaguy_professional.professional.presentation.on_going_detail

import com.example.callaguy_professional.professional.domain.ServiceRequests
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailAction

interface OnGoingDetailAction {
    data class OnGoingDetailChange(val service : ServiceRequests) : OnGoingDetailAction
    data class OnUpdateStatusClick(val serviceId : Int) : OnGoingDetailAction
}