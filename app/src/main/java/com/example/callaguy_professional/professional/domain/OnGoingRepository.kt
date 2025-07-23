package com.example.callaguy_professional.professional.domain

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus

interface OnGoingRepository {
    suspend fun getAcceptedJobs() : Result<List<ServiceRequests> , DataError.Remote>
    suspend fun updateJobStatus(serviceId : Int , status : ServiceRequestStatus) : Result<String , DataError.Remote>
}