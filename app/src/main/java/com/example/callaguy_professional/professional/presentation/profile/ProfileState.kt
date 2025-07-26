package com.example.callaguy_professional.professional.presentation.profile

import com.example.callaguy_professional.core.presentation.components.UiText
import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo

data class ProfileState(
    val isLoading : Boolean = false,
    val isProfileStateLoading : Boolean = false ,
    val info : ProfessionalProfileInfo? = null,
    val isSuccess : Boolean = false,
    val profileUpdateError : String? = null,
    val profileStateError : UiText? = null
)
