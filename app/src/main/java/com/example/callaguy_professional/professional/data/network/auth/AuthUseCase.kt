package com.example.callaguy_professional.professional.data.network.auth

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result

interface AuthUseCase {
    suspend fun isApproved(): Result<Boolean , DataError.Remote>
}