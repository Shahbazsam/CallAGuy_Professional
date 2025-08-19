package com.example.callaguy_professional.professional.data.network.auth

import com.example.callaguy_professional.core.data.safeCall
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.presentation.AppDefaults
import com.example.callaguy_professional.professional.data.dto.LoginRequestDto
import com.example.callaguy_professional.professional.data.dto.LoginResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthUseCaseImpl(
    private val httpClient: HttpClient
) : AuthUseCase {
    override suspend fun isApproved(): Result<Boolean, DataError.Remote> {
        return safeCall<Boolean> {
            httpClient.get(
                urlString = "${AppDefaults.PROFESSIONAL_AUTH}/is_approved"
            )
        }
    }

    override suspend fun login(loginRequestDto: LoginRequestDto): Result<LoginResponseDto, DataError.Remote> {
        return safeCall<LoginResponseDto> {
            httpClient.post(
                urlString = "${AppDefaults.PROFESSIONAL_AUTH}/login"
            ){
                contentType(ContentType.Application.Json)
                setBody(loginRequestDto)
            }
        }
    }
}