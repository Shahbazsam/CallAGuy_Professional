package com.example.callaguy_professional.professional.data.mappers

import com.example.callaguy_professional.professional.data.dto.ProfessionalProfileInfoDto
import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo

fun ProfessionalProfileInfoDto.toProfileInfo() : ProfessionalProfileInfo {
    return ProfessionalProfileInfo(
        userName = userName,
        email = email,
        experience = experience,
        isApproved = isApproved,
        profilePicture = profilePicture,
        services = services
    )
}