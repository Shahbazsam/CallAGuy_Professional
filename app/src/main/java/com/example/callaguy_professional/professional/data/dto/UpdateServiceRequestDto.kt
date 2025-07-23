package com.example.callaguy_professional.professional.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class UpdateServiceRequestDto(
    val requestId : Int,
    val newStatus : ServiceRequestStatus
)
