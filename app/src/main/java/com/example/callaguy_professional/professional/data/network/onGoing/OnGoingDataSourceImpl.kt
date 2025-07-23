package com.example.callaguy_professional.professional.data.network.onGoing

import com.example.callaguy_professional.core.data.safeCall
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.presentation.AppDefaults
import com.example.callaguy_professional.professional.data.dto.ServiceRequestDto
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.data.dto.UpdateServiceRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class OnGoingDataSourceImpl(
    private val httpClient: HttpClient
) : OnGoingDataSource {
    override suspend fun getAcceptedJobs(): Result<List<ServiceRequestDto>, DataError.Remote> {
        return safeCall <List<ServiceRequestDto>> {
            httpClient.get(
                urlString = "${AppDefaults.PROFESSIONAL_SERVICE}/my_orders"
            )
        }
    }

    override suspend fun updateJobStatus(
        updateServiceRequestDto: UpdateServiceRequestDto
    ): Result<String, DataError.Remote> {
        return safeCall <String> {
            httpClient.post(
                urlString = "${AppDefaults.PROFESSIONAL_SERVICE}/update_status"
            ){
                contentType(ContentType.Application.Json)
                setBody(
                    updateServiceRequestDto
                )
            }
        }
    }

}