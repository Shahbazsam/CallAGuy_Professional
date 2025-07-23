package com.example.callaguy_professional.professional.domain

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result

interface ServiceRepository {
    suspend fun getServiceList(): Result<List<ServiceRequests> , DataError.Remote>
    suspend fun onAcceptJob( serviceId : Int ) : Result<String , DataError.Remote>
}