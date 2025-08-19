package com.example.callaguy_professional.professional.presentation.onGoing

import app.cash.turbine.test
import com.example.callaguy_professional.MainCoroutineRule
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.domain.OnGoingRepository
import com.example.callaguy_professional.professional.domain.ServiceRequests
import com.example.callaguy_professional.professional.presentation.on_going.OnGoingViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OnGoingViewModelTest {

    @get:Rule
    val mainRule = MainCoroutineRule()

    private lateinit var repo: OnGoingRepository

    @Before
    fun setup() {
        repo = mockk()
    }

    @Test
    fun `onStart loads and partitions accepted vs past - success`() = runTest {
        val acceptedItem =
            mockk<ServiceRequests>(relaxed = true) { every { status } returns ServiceRequestStatus.ACCEPTED }
        val pastItem =
            mockk<ServiceRequests>(relaxed = true) { every { status } returns ServiceRequestStatus.COMPLETED }

        coEvery { repo.getAcceptedJobs() } returns Result.Success(listOf(acceptedItem, pastItem))

        val vm = OnGoingViewModel(repo)

        val initial = vm.state.value
        assertTrue(initial.isLoading)
        assertTrue(initial.accepted.isEmpty())
        assertTrue(initial.past.isEmpty())
        assertNull(initial.errorMessage)

        vm.state.test {
            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val done = awaitItem()
            assertFalse(done.isLoading)
            assertEquals(listOf(acceptedItem), done.accepted)
            assertEquals(listOf(pastItem), done.past)
            assertNull(done.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onStart error clears lists and sets errorMessage`() = runTest {
        val err = DataError.Remote.REQUEST_TIMEOUT
        coEvery { repo.getAcceptedJobs() } returns Result.Error(err)

        val vm = OnGoingViewModel(repo)

        val initial = vm.state.value
        assertTrue(initial.isLoading)
        assertTrue(initial.accepted.isEmpty())
        assertTrue(initial.past.isEmpty())
        assertNull(initial.errorMessage)

        vm.state.test {
            assertTrue(awaitItem().isLoading)

            val done = awaitItem()
            assertFalse(done.isLoading)
            assertTrue(done.accepted.isEmpty())
            assertTrue(done.past.isEmpty())
            assertEquals(err.toString(), done.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
