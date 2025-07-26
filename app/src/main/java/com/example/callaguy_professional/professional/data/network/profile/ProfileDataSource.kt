package com.example.callaguy_professional.professional.data.network.profile

import android.content.Context
import android.net.Uri
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.dto.ProfessionalProfileInfoDto

interface ProfileDataSource {
    suspend fun uploadProfilePicture(uri : Uri, context: Context) : Result<String , DataError.Remote>
    suspend fun getProfileInfo() : Result<ProfessionalProfileInfoDto , DataError.Remote>
}