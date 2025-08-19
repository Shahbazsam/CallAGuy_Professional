package com.example.callaguy_professional.professional.data.repository

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.dto.LoginRequestDto
import com.example.callaguy_professional.professional.data.dto.LoginResponseDto
import com.example.callaguy_professional.professional.data.network.auth.AuthUseCase
import com.example.callaguy_professional.professional.domain.AuthRepository
import com.example.callaguy_professional.professional.domain.LoginRequest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {

    private lateinit var useCase: AuthUseCase
    private lateinit var repository: AuthRepository

    @Before
    fun setup() {
        useCase = mockk()
        repository = AuthRepositoryImpl(useCase)
    }

    @Test
    fun `login maps dto to model on success`() = runTest {

        val email = "pro@dev.com"
        val password = "Secret1!"
        val req = LoginRequest(email, password)
        val dtoReq = LoginRequestDto(email, password)
        val dtoResp = LoginResponseDto(token = "jwt.token")
        coEvery { useCase.login(dtoReq) } returns Result.Success(dtoResp)

        val result = repository.login(req)

        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals("jwt.token", data.token)
    }

    @Test
    fun `login propagates error`() = runTest {
        val email = "pro@dev.com"
        val password = "Secret1!"
        val req = LoginRequest(email, password)
        val dtoReq = LoginRequestDto(email, password)

        val err = DataError.Remote.REQUEST_TIMEOUT
        coEvery { useCase.login(dtoReq) } returns Result.Error(err)

        val result = repository.login(req)

        assertTrue(result is Result.Error)
        assertEquals(err, (result as Result.Error).error)
    }

    @Test
    fun `isApproved success is propagated`() = runTest {
        coEvery { useCase.isApproved() } returns Result.Success(true)

        val result = repository.isApproved()

        assertTrue(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `isApproved error is propagated`() = runTest {
        val err = DataError.Remote.UNKNOWN
        coEvery { useCase.isApproved() } returns Result.Error(err)

        val result = repository.isApproved()

        assertTrue(result is Result.Error)
        assertEquals(err, (result as Result.Error).error)
    }
}
