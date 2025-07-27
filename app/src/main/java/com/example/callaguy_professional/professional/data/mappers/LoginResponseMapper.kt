package com.example.callaguy_professional.professional.data.mappers

import com.example.callaguy_professional.professional.data.dto.LoginResponseDto
import com.example.callaguy_professional.professional.domain.LoginResponse


fun LoginResponseDto.toModel() : LoginResponse {
    return LoginResponse(
        token = token
    )
}