package com.example.callaguy_professional.professional.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequestDto(
    val email : String,
    val password : String
)
