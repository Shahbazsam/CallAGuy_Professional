package com.example.callaguy_professional.professional.domain

import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ServiceRequests(
    val id : Int,
    val customerId : Int,
    val professionalId : Int ?,
    val amount : BigDecimal,
    val subService : String,
    val image : String?,
    val subServiceId : Int,
    val status : ServiceRequestStatus,
    val preferredDate : LocalDate,
    val preferredTime : LocalTime,
    val address: String,
    val specialInstructions : String?,
    val createdAt : LocalDateTime
)
