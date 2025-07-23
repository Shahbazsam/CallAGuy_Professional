package com.example.callaguy_professional.professional.domain

data class ProfessionalProfileInfo(
    val userName : String,
    val email : String,
    val experience : Int,
    val isApproved : Boolean,
    val profilePicture : String?,
    val services : List<String>
)
