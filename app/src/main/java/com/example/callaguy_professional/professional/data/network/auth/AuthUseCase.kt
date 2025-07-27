package com.example.callaguy_professional.professional.data.network.auth

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.dto.LoginRequestDto
import com.example.callaguy_professional.professional.data.dto.LoginResponseDto

interface AuthUseCase {
    suspend fun isApproved(): Result<Boolean , DataError.Remote>
    suspend fun login(loginRequestDto: LoginRequestDto) : Result<LoginResponseDto , DataError.Remote>
}