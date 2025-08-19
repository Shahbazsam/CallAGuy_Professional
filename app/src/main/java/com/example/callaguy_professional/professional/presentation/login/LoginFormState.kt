package com.example.callaguy_professional.professional.presentation.login

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val isApproved : Boolean = false ,
    val isLoginError: String? = null
)
