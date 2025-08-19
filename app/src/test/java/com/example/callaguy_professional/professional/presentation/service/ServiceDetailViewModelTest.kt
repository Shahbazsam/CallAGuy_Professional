package com.example.callaguy_professional.professional.presentation.service

import app.cash.turbine.test
import com.example.callaguy_professional.MainCoroutineRule
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.domain.ServiceRepository
import com.example.callaguy_professional.professional.domain.ServiceRequests
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailAction
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailViewModel
import io.mockk.coEvery
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
class ServiceDetailViewModelTest {

    @get:Rule
    val mainRule = MainCoroutineRule()

    private lateinit var repo: ServiceRepository

    @Before
    fun setup() {
        repo = mockk()
    }

    @Test
    fun `OnServiceDetailChange updates service in state`() = runTest {
        val vm = ServiceDetailViewModel(repo)
        val service = mockk<ServiceRequests>(relaxed = true)

        vm.state.test {
            val initial = awaitItem()
            assertNull(initial.service)

            vm.onAction(ServiceDetailAction.OnServiceDetailChange(service))

            val updated = awaitItem()
            assertEquals(service, updated.service)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnAcceptJobClick - success sets isSuccessful true`() = runTest {
        coEvery { repo.onAcceptJob(42) } returns Result.Success("OK")

        val vm = ServiceDetailViewModel(repo)

        // initial
        val initial = vm.state.value
        assertFalse(initial.isLoading)
        assertFalse(initial.isSuccessful)
        assertNull(initial.errorMessage)

        vm.state.test {
            awaitItem()
            vm.onAction(ServiceDetailAction.OnAcceptJobClick(serviceId = 42))

            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val done = awaitItem()
            assertFalse(done.isLoading)
            assertTrue(done.isSuccessful)
            assertNull(done.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }

        advanceUntilIdle()
    }

    @Test
    fun `OnAcceptJobClick - error sets errorMessage`() = runTest {
        val err = DataError.Remote.UNKNOWN
        coEvery { repo.onAcceptJob(7) } returns Result.Error(err)

        val vm = ServiceDetailViewModel(repo)

        // initial
        val initial = vm.state.value
        assertFalse(initial.isLoading)
        assertFalse(initial.isSuccessful)
        assertNull(initial.errorMessage)

        vm.state.test {
            awaitItem()
            vm.onAction(ServiceDetailAction.OnAcceptJobClick(serviceId = 7))

            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val done = awaitItem()
            assertFalse(done.isLoading)
            assertFalse(done.isSuccessful)
            assertEquals(err.toString(), done.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }

        advanceUntilIdle()
    }
}
