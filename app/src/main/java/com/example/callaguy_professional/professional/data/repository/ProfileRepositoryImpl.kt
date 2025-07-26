package com.example.callaguy_professional.professional.data.repository

import android.content.Context
import android.net.Uri
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.domain.map
import com.example.callaguy_professional.professional.data.mappers.toProfileInfo
import com.example.callaguy_professional.professional.data.network.profile.ProfileDataSource
import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo
import com.example.callaguy_professional.professional.domain.ProfileRepository

class ProfileRepositoryImpl(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun uploadProfilePicture(
        uri: Uri,
        context: Context
    ): Result<String, DataError.Remote> {
        return profileDataSource.uploadProfilePicture(uri, context)
    }

    override suspend fun getProfileInfo(): Result<ProfessionalProfileInfo, DataError.Remote> {
        return profileDataSource
            .getProfileInfo()
            .map { profileInfo ->
                profileInfo.toProfileInfo()
            }
    }
}