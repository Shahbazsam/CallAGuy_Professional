package com.example.callaguy_professional.professional.data.network.onGoing

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.dto.ServiceRequestDto
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.data.dto.UpdateServiceRequestDto

interface OnGoingDataSource {
    suspend fun getAcceptedJobs() : Result<List<ServiceRequestDto> , DataError.Remote>
    suspend fun updateJobStatus(updateServiceRequestDto: UpdateServiceRequestDto) : Result<String , DataError.Remote>
}