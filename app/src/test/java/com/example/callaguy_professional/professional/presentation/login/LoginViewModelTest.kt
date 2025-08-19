package com.example.callaguy_professional.professional.presentation.login

import com.example.callaguy_professional.MainCoroutineRule
import com.example.callaguy_professional.core.auth.TokenProvider
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.domain.validation.ValidateEmail
import com.example.callaguy_professional.core.domain.validation.ValidatePassword
import com.example.callaguy_professional.core.domain.validation.ValidationResult
import com.example.callaguy_professional.professional.domain.AuthRepository
import com.example.callaguy_professional.professional.domain.LoginRequest
import com.example.callaguy_professional.professional.domain.LoginResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var validateEmail: ValidateEmail
    private lateinit var validatePassword: ValidatePassword
    private lateinit var authRepository: AuthRepository
    private lateinit var tokenProvider: TokenProvider

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        validateEmail = mockk()
        validatePassword = mockk()
        authRepository = mockk()
        tokenProvider = mockk()

        every { tokenProvider.saveToken(any()) } just Runs

        viewModel = LoginViewModel(
            validateEmail = validateEmail,
            validatePassword = validatePassword,
            authRepository = authRepository,
            tokenProvider = tokenProvider
        )
    }

    private fun valid() = ValidationResult(successful = true)
    private fun invalid(msg: String) = ValidationResult(successful = false, errorMessage = msg)

    @Test
    fun `EmailChanged updates state`() = runTest {
        val email = "pro@dev.com"

        viewModel.onEvent(LoginFormEvent.EmailChanged(email))

        assertEquals(email, viewModel.state.value.email)
    }

    @Test
    fun `PasswordChanged updates state`() = runTest {
        val pwd = "S3cret!"

        viewModel.onEvent(LoginFormEvent.PasswordChanged(pwd))

        assertEquals(pwd, viewModel.state.value.password)
    }

    @Test
    fun `Submit with invalid fields sets errors and does not call repository`() = runTest {

        every { validateEmail.execute(any()) } returns invalid("Bad email")
        every { validatePassword.execute(any()) } returns invalid("Bad password")

        viewModel.onEvent(LoginFormEvent.EmailChanged("nope"))
        viewModel.onEvent(LoginFormEvent.PasswordChanged("123"))

        viewModel.onEvent(LoginFormEvent.Submit)

        val s = viewModel.state.value
        assertEquals("Bad email", s.emailError)
        assertEquals("Bad password", s.passwordError)
        assertFalse(s.isLoading)
        coVerify(exactly = 0) { authRepository.login(any()) }
        coVerify(exactly = 0) { authRepository.isApproved() }
        confirmVerified(authRepository)
    }

    @Test
    fun `Submit success - saves token, checks approval, updates state with approved true`() =
        runTest {

            every { validateEmail.execute(any()) } returns valid()
            every { validatePassword.execute(any()) } returns valid()

            val email = "pro@dev.com"
            val password = "StrongPass1!"
            viewModel.onEvent(LoginFormEvent.EmailChanged(email))
            viewModel.onEvent(LoginFormEvent.PasswordChanged(password))

            val token = "jwt.token.value"
            coEvery {
                authRepository.login(LoginRequest(email, password))
            } returns Result.Success(LoginResponse(token))

            coEvery { authRepository.isApproved() } returns Result.Success(true)


            viewModel.onEvent(LoginFormEvent.Submit)
            advanceUntilIdle()

            coVerify(exactly = 1) { tokenProvider.saveToken(token) }
            val s = viewModel.state.value
            assertFalse(s.isLoading)
            assertTrue(s.isLoginSuccessful)
            assertTrue(s.isApproved)
            assertNull(s.isLoginError)
        }

    @Test
    fun `Submit success - isApproved returns false`() = runTest {
        every { validateEmail.execute(any()) } returns valid()
        every { validatePassword.execute(any()) } returns valid()

        val email = "pro@dev.com"
        val password = "StrongPass1!"
        viewModel.onEvent(LoginFormEvent.EmailChanged(email))
        viewModel.onEvent(LoginFormEvent.PasswordChanged(password))

        val token = "jwt.token.value"
        coEvery { authRepository.login(LoginRequest(email, password)) } returns Result.Success(
            LoginResponse(token)
        )
        coEvery { authRepository.isApproved() } returns Result.Success(false)

        viewModel.onEvent(LoginFormEvent.Submit)
        advanceUntilIdle()

        coVerify { tokenProvider.saveToken(token) }
        val s = viewModel.state.value
        assertFalse(s.isLoading)
        assertTrue(s.isLoginSuccessful)
        assertEquals(false, s.isApproved)
        assertNull(s.isLoginError)
    }

    @Test
    fun `Submit - login error sets isLoginError and stops loading`() = runTest {
        every { validateEmail.execute(any()) } returns valid()
        every { validatePassword.execute(any()) } returns valid()

        val email = "pro@dev.com"
        val password = "StrongPass1!"
        viewModel.onEvent(LoginFormEvent.EmailChanged(email))
        viewModel.onEvent(LoginFormEvent.PasswordChanged(password))

        val error = DataError.Remote.UNKNOWN
        coEvery { authRepository.login(LoginRequest(email, password)) } returns Result.Error(error)

        viewModel.onEvent(LoginFormEvent.Submit)
        advanceUntilIdle()

        val s = viewModel.state.value
        assertFalse(s.isLoading)
        assertEquals(error.toString(), s.isLoginError)
        assertFalse(s.isLoginSuccessful)
        coVerify(exactly = 0) { tokenProvider.saveToken(any()) }
        coVerify(exactly = 0) { authRepository.isApproved() }
    }


    @Test
    fun `Submit - login success but isApproved error sets isLoginError (loginSuccessful stays false)`() =
        runTest {
            every { validateEmail.execute(any()) } returns valid()
            every { validatePassword.execute(any()) } returns valid()

            val email = "pro@dev.com"
            val password = "StrongPass1!"
            viewModel.onEvent(LoginFormEvent.EmailChanged(email))
            viewModel.onEvent(LoginFormEvent.PasswordChanged(password))

            val token = "jwt.token.value"
            coEvery { authRepository.login(LoginRequest(email, password)) } returns Result.Success(
                LoginResponse(token)
            )
            val error = DataError.Remote.UNKNOWN
            coEvery { authRepository.isApproved() } returns Result.Error(error)

            viewModel.onEvent(LoginFormEvent.Submit)
            advanceUntilIdle()

            coVerify { tokenProvider.saveToken(token) }
            val s = viewModel.state.value
            assertFalse(s.isLoading)
            assertEquals(error.toString(), s.isLoginError)

            assertFalse(s.isLoginSuccessful)
            assertFalse(s.isApproved)
        }
}
