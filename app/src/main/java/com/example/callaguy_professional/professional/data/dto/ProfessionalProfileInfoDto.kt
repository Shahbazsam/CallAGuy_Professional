package com.example.callaguy_professional.professional.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class ProfessionalProfileInfoDto(
    val userName : String,
    val email : String,
    val experience : Int,
    val isApproved : Boolean,
    val profilePicture : String?,
    val services : List<String>
)
