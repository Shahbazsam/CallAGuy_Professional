package com.example.callaguy_professional.professional.data.repository

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.domain.map
import com.example.callaguy_professional.professional.data.dto.AcceptServiceRequestDto
import com.example.callaguy_professional.professional.data.mappers.toServiceRequest
import com.example.callaguy_professional.professional.data.network.serviceList.ServiceDataSource
import com.example.callaguy_professional.professional.domain.ServiceRepository
import com.example.callaguy_professional.professional.domain.ServiceRequests

class ServiceRepoImpl(
    private val serviceDataSource: ServiceDataSource
) : ServiceRepository {
    override suspend fun getServiceList(): Result<List<ServiceRequests>, DataError.Remote> {
        return serviceDataSource
            .getServices()
            .map { dtoList ->
                dtoList.map { dto ->
                    dto.toServiceRequest()
                }
            }
    }

    override suspend fun onAcceptJob(serviceId: Int): Result<String, DataError.Remote> {
        return serviceDataSource
            .acceptJob(
                AcceptServiceRequestDto(
                    requestId = serviceId
                )
            )
    }
}