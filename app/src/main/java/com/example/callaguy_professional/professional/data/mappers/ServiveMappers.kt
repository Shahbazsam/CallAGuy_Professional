package com.example.callaguy_professional.professional.data.mappers

import com.example.callaguy_professional.professional.data.dto.ServiceRequestDto
import com.example.callaguy_professional.professional.domain.ServiceRequests


fun ServiceRequestDto.toServiceRequest() : ServiceRequests {
    return ServiceRequests(
        id = id,
        customerId = customerId,
        professionalId = professionalId,
        amount = amount,
        subService = subService,
        image = image,
        subServiceId = subServiceId,
        status = status,
        preferredDate = preferredDate,
        preferredTime = preferredTime,
        address = address,
        specialInstructions = specialInstructions,
        createdAt = createdAt
    )
}