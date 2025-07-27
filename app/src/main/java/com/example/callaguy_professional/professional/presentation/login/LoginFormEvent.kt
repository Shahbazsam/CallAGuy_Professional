package com.example.callaguy_professional.professional.presentation.login

sealed interface LoginFormEvent {
    data class EmailChanged(val email : String) : LoginFormEvent
    data class PasswordChanged(val password : String) : LoginFormEvent

    data object Submit : LoginFormEvent
}