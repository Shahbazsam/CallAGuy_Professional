package com.example.callaguy_professional.professional.presentation.sharedViewModel

import app.cash.turbine.test
import com.example.callaguy_professional.professional.domain.ServiceRequests
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class OnGoingSharedViewModelTest {

    @Test
    fun `selectedOnGoing emits null, then value, then null`() = runTest {
        val vm = OnGoingSharedViewModel()
        val dummy = mockk<ServiceRequests>(relaxed = true)

        vm.selectedOnGoing.test {
            assertNull(awaitItem())

            vm.onSelectedOnGoing(dummy)
            assertEquals(dummy, awaitItem())

            vm.onSelectedOnGoing(null)
            assertNull(awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
