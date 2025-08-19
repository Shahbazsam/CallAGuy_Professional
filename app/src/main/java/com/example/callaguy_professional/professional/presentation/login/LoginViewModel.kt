package com.example.callaguy_professional.professional.presentation.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy_professional.core.auth.TokenProvider
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.core.domain.validation.ValidateEmail
import com.example.callaguy_professional.core.domain.validation.ValidatePassword
import com.example.callaguy_professional.professional.domain.AuthRepository
import com.example.callaguy_professional.professional.domain.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val authRepository: AuthRepository,
    private val tokenProvider: TokenProvider
) : ViewModel() {

    private val _state = MutableStateFlow(LoginFormState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is LoginFormEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            LoginFormEvent.Submit -> submit()
        }
    }

    private fun submit() {
        val emailResult = validateEmail.execute(state.value.email)
        val passwordResult = validatePassword.execute(state.value.password)

        val hasError = listOf(
            emailResult, passwordResult
        ).any { !it.successful }

        if (hasError) {
            _state.update {
                it.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
            }
            return
        }
        if (!hasError) {
            viewModelScope.launch {
                _state.update {
                    it.copy(isLoading = true)
                }
                authRepository
                    .login(
                        LoginRequest(
                            email = _state.value.email,
                            password = _state.value.password
                        )
                    )
                    .onSuccess { result ->
                        tokenProvider.saveToken(result.token)
                        authRepository
                            .isApproved()
                            .onSuccess { response ->
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isLoginSuccessful = true,
                                        isApproved = response
                                    )
                                }
                            }
                            .onError { error ->
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isLoginError = error.toString()
                                    )
                                }
                            }

                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isLoginError = error.toString()
                            )
                        }
                    }

            }
        }
    }
}