package com.example.callaguy_professional.professional.domain

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result

interface AuthRepository {
    suspend fun isApproved() : Result<Boolean , DataError.Remote>
}