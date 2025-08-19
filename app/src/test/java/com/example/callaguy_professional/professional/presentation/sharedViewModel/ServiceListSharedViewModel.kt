package com.example.callaguy_professional.professional.presentation.sharedViewModel

import app.cash.turbine.test
import com.example.callaguy_professional.professional.domain.ServiceRequests
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ServiceListSharedViewModelTest {

    @Test
    fun `selectedService emits null, then value, then null`() = runTest {
        val vm = ServiceListSharedViewModel()
        val dummy = mockk<ServiceRequests>(relaxed = true)

        vm.selectedService.test {
            assertNull(awaitItem())

            vm.onSelectedService(dummy)
            assertEquals(dummy, awaitItem())

            vm.onSelectedService(null)
            assertNull(awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
