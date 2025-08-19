package com.example.callaguy_professional.professional.presentation.service

import app.cash.turbine.test
import com.example.callaguy_professional.MainCoroutineRule
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.domain.ServiceRepository
import com.example.callaguy_professional.professional.domain.ServiceRequests
import com.example.callaguy_professional.professional.presentation.service_list.ServiceRequestViewModel
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
class ServiceRequestViewModelTest {

    @get:Rule
    val mainRule = MainCoroutineRule()

    private lateinit var repo: ServiceRepository

    @Before
    fun setup() {
        repo = mockk()
    }

    @Test
    fun `onStart loads services - success`() = runTest {
        val items = listOf(mockk<ServiceRequests>(relaxed = true))
        coEvery { repo.getServiceList() } returns Result.Success(items)

        val vm = ServiceRequestViewModel(repo)

        val initial = vm.state.value
        assertTrue(initial.isLoading)
        assertTrue(initial.serviceList.isEmpty())
        assertNull(initial.errorMessage)

        vm.state.test {
            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val done = awaitItem()
            assertFalse(done.isLoading)
            assertEquals(items.size, done.serviceList.size)
            assertNull(done.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }

        advanceUntilIdle()
    }

    @Test
    fun `onStart loads services - error`() = runTest {
        val err = DataError.Remote.REQUEST_TIMEOUT
        coEvery { repo.getServiceList() } returns Result.Error(err)

        val vm = ServiceRequestViewModel(repo)

        val initial = vm.state.value
        assertTrue(initial.isLoading)
        assertTrue(initial.serviceList.isEmpty())
        assertNull(initial.errorMessage)

        vm.state.test {
            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val done = awaitItem()
            assertFalse(done.isLoading)
            assertTrue(done.serviceList.isEmpty())
            assertEquals(err.toString(), done.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }

        advanceUntilIdle()
    }
}
