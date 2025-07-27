package com.example.callaguy_professional.professional.data.repository

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.domain.map
import com.example.callaguy_professional.professional.data.mappers.toDto
import com.example.callaguy_professional.professional.data.network.auth.AuthUseCase
import com.example.callaguy_professional.professional.domain.AuthRepository
import com.example.callaguy_professional.professional.domain.LoginRequest
import com.example.callaguy_professional.professional.domain.LoginResponse

class AuthRepositoryImpl(
    private val authUseCase: AuthUseCase
) : AuthRepository {
    override suspend fun isApproved(): Result<Boolean, DataError.Remote> {
        return authUseCase.isApproved()
    }

    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse, DataError.Remote> {
        return authUseCase
            .login(loginRequest.toDto())
            .map { result ->
                LoginResponse(
                    token = result.token
                )
            }
    }
}