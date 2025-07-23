package com.example.callaguy_professional.professional.data.network.auth

import com.example.callaguy_professional.core.data.safeCall
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.presentation.AppDefaults
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class AuthUseCaseImpl(
    private val httpClient: HttpClient
) : AuthUseCase {
    override suspend fun isApproved(): Result<Boolean, DataError.Remote> {
        return safeCall<Boolean> {
            httpClient.get(
                urlString = "${AppDefaults.PROFESSIONAL_SERVICE}/is_approved"
            )
        }
    }
}