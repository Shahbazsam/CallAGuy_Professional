package com.example.callaguy_professional.professional.data.mappers

import com.example.callaguy_professional.professional.data.dto.LoginRequestDto
import com.example.callaguy_professional.professional.domain.LoginRequest

fun LoginRequest.toDto() : LoginRequestDto {
    return LoginRequestDto(
        email = email,
        password = password
    )
}