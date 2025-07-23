package com.example.callaguy_professional.professional.data.network.serviceList

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.dto.AcceptServiceRequestDto
import com.example.callaguy_professional.professional.data.dto.ServiceRequestDto

interface ServiceDataSource {
    suspend fun getServices() : Result<List<ServiceRequestDto> , DataError.Remote>
    suspend fun acceptJob(acceptServiceRequestDto: AcceptServiceRequestDto) : Result<String , DataError.Remote>
}