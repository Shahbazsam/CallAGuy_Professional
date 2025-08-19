package com.example.callaguy_professional.professional.presentation.onGoing

import app.cash.turbine.test
import com.example.callaguy_professional.MainCoroutineRule
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.domain.OnGoingRepository
import com.example.callaguy_professional.professional.domain.ServiceRequests
import com.example.callaguy_professional.professional.presentation.on_going_detail.OnGoingDetailAction
import com.example.callaguy_professional.professional.presentation.on_going_detail.OnGoingDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OnGoingDetailViewModelTest {

    @get:Rule
    val mainRule = MainCoroutineRule()

    private lateinit var repo: OnGoingRepository

    @Before
    fun setup() {
        repo = mockk()
    }

    @Test
    fun `OnGoingDetailChange updates service`() = runTest {
        val vm = OnGoingDetailViewModel(repo)
        val s = mockk<ServiceRequests>(relaxed = true)

        vm.state.test {
            val initial = awaitItem()
            assertNull(initial.service)
            assertFalse(initial.isLoading)
            assertFalse(initial.isSuccessful)
            assertNull(initial.errorMessage)

            vm.onAction(OnGoingDetailAction.OnGoingDetailChange(s))
            
            val updated = awaitItem()
            assertEquals(s, updated.service)
            assertFalse(updated.isLoading)
            assertFalse(updated.isSuccessful)
            assertNull(updated.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnUpdateStatusClick success - loading then success, clears error`() = runTest {
        coEvery {
            repo.updateJobStatus(
                5,
                ServiceRequestStatus.COMPLETED
            )
        } returns Result.Success("OK")
        val vm = OnGoingDetailViewModel(repo)

        vm.state.test {
            awaitItem()

            vm.onAction(OnGoingDetailAction.OnUpdateStatusClick(serviceId = 5))

            val loading = awaitItem()
            assertTrue(loading.isLoading)
            assertFalse(loading.isSuccessful)

            val done = awaitItem()
            assertFalse(done.isLoading)
            assertTrue(done.isSuccessful)
            assertNull(done.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnUpdateStatusClick error - loading then error, not successful`() = runTest {
        val err = DataError.Remote.UNKNOWN
        coEvery {
            repo.updateJobStatus(
                9,
                ServiceRequestStatus.COMPLETED
            )
        } returns Result.Error(err)
        val vm = OnGoingDetailViewModel(repo)

        vm.state.test {
            awaitItem()

            vm.onAction(OnGoingDetailAction.OnUpdateStatusClick(serviceId = 9))

            val loading = awaitItem()
            assertTrue(loading.isLoading)
            assertFalse(loading.isSuccessful)

            val done = awaitItem()
            assertFalse(done.isLoading)
            assertFalse(done.isSuccessful)
            assertEquals(err.toString(), done.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
