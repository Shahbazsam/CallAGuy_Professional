package com.example.callaguy_professional.professional.presentation.login

import com.example.callaguy_professional.core.presentation.components.UiText

data class LoginFormState(
    val email : String = "",
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val isLoading : Boolean = false,
    val isLoginSuccessful : Boolean = false,
    val isLoginError : String? = null
)
