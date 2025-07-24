package com.example.callaguy_professional.professional.presentation.profile

import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo

data class ProfileState(
    val isLoading : Boolean = false,
    val info : ProfessionalProfileInfo? = null,
    val errorMessage : String? = null
)
