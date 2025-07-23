package com.example.callaguy_professional.professional.data.network.serviceList

import com.example.callaguy_professional.core.data.safeCall
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.presentation.AppDefaults
import com.example.callaguy_professional.professional.data.dto.AcceptServiceRequestDto
import com.example.callaguy_professional.professional.data.dto.ServiceRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ServiceDataSourceImpl(
    private val httpClient: HttpClient
) : ServiceDataSource {
    override suspend fun getServices(): Result<List<ServiceRequestDto>, DataError.Remote> {
        return safeCall<List<ServiceRequestDto>> {
            httpClient.get(
                urlString = AppDefaults.PROFESSIONAL_SERVICE
            )
        }
    }

    override suspend fun acceptJob(acceptServiceRequestDto: AcceptServiceRequestDto): Result<String, DataError.Remote> {
        return safeCall<String> {
            httpClient.post(
                urlString = "${AppDefaults.PROFESSIONAL_SERVICE}/accept"
            ) {
                contentType(ContentType.Application.Json)
                setBody(acceptServiceRequestDto)
            }
        }
    }
}