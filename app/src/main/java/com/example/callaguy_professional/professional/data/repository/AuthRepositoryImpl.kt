package com.example.callaguy_professional.professional.data.repository

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.network.auth.AuthUseCase
import com.example.callaguy_professional.professional.domain.AuthRepository

class AuthRepositoryImpl(
    private val authUseCase: AuthUseCase
) : AuthRepository {
    override suspend fun isApproved(): Result<Boolean, DataError.Remote> {
        return authUseCase.isApproved()
    }
}