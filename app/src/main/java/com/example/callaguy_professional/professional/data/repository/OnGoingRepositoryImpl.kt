package com.example.callaguy_professional.professional.data.repository

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.domain.map
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.data.dto.UpdateServiceRequestDto
import com.example.callaguy_professional.professional.data.mappers.toServiceRequest
import com.example.callaguy_professional.professional.data.network.onGoing.OnGoingDataSource
import com.example.callaguy_professional.professional.domain.OnGoingRepository
import com.example.callaguy_professional.professional.domain.ServiceRequests

class OnGoingRepositoryImpl(
    private val onGoingDataSource: OnGoingDataSource
) : OnGoingRepository {
    override suspend fun getAcceptedJobs(): Result<List<ServiceRequests>, DataError.Remote> {
        return onGoingDataSource
            .getAcceptedJobs()
            .map { ordersList ->
                ordersList.map { order ->
                    order.toServiceRequest()
                }
            }
    }

    override suspend fun updateJobStatus(
        serviceId: Int,
        status: ServiceRequestStatus
    ): Result<String, DataError.Remote> {
        return onGoingDataSource.updateJobStatus(
            UpdateServiceRequestDto(
                requestId = serviceId,
                newStatus = status
            )
        )
    }
}