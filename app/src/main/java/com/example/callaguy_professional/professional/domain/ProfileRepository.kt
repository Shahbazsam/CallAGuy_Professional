package com.example.callaguy_professional.professional.domain

import android.content.Context
import android.net.Uri
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result

interface ProfileRepository {

    suspend fun uploadProfilePicture(
        uri: Uri,
        context: Context
    ) : Result<String , DataError.Remote>

    suspend fun getProfileInfo() : Result<ProfessionalProfileInfo, DataError.Remote>
}