package com.example.callaguy_professional.professional.presentation.splash

import com.example.callaguy_professional.MainCoroutineRule
import com.example.callaguy_professional.core.auth.TokenProvider
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.professional.domain.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import com.example.callaguy_professional.core.domain.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    val mainRule = MainCoroutineRule()

    private lateinit var tokenProvider: TokenProvider
    private lateinit var repository: AuthRepository
    private lateinit var vm: SplashViewModel

    @Before
    fun setup() {
        tokenProvider = mockk()
        repository = mockk()
        vm = SplashViewModel(tokenProvider, repository)
    }

    @Test
    fun `when no token - loggedBefore false and repo not called`() = runTest {

        every { tokenProvider.getToken() } returns null

        vm.onAction(SplashAction.CheckApproval)
        advanceTimeBy(1000)
        advanceUntilIdle()

        val s = vm.state.value
        assertFalse(s.loggedBefore == true)
        assertNull(s.isApproved)
        assertNull(s.errorMessage)
        coVerify(exactly = 0) { repository.isApproved() }
        confirmVerified(repository)
    }

    @Test
    fun `token present and approved - isApproved true, loggedBefore true`() = runTest {
        every { tokenProvider.getToken() } returns "jwt"
        coEvery { repository.isApproved() } returns Result.Success(true)

        vm.onAction(SplashAction.CheckApproval)
        advanceTimeBy(1000)
        advanceUntilIdle()

        val s = vm.state.value
        assertTrue(s.loggedBefore == true)
        assertEquals(true, s.isApproved)
        assertNull(s.errorMessage)
        coVerify(exactly = 1) { repository.isApproved() }
    }

    @Test
    fun `token present and repo error - isApproved false, errorMessage set`() = runTest {
        every { tokenProvider.getToken() } returns "jwt"
        val err = DataError.Remote.REQUEST_TIMEOUT
        coEvery { repository.isApproved() } returns Result.Error(err)

        vm.onAction(SplashAction.CheckApproval)
        advanceTimeBy(1000)
        advanceUntilIdle()

        val s = vm.state.value
        assertEquals(false, s.isApproved)
        assertEquals(err.toString(), s.errorMessage)
        assertNull(s.loggedBefore)
        coVerify(exactly = 1) { repository.isApproved() }
    }
}
