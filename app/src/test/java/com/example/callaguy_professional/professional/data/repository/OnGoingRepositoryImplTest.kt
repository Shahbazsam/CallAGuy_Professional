package com.example.callaguy_professional.professional.data.repository


import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.professional.data.dto.ServiceRequestDto
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.data.network.onGoing.OnGoingDataSource
import com.example.callaguy_professional.professional.domain.OnGoingRepository
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.professional.data.dto.UpdateServiceRequestDto
import com.example.callaguy_professional.professional.domain.ServiceRequests
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class OnGoingRepositoryImplTest {

    private lateinit var ds: OnGoingDataSource
    private lateinit var repo: OnGoingRepository

    @Before
    fun setup() {
        ds = mockk()
        repo = OnGoingRepositoryImpl(ds)
    }

    @Test
    fun `getAcceptedJobs maps dto to domain`() = runTest {
        val dto = ServiceRequestDto(
            id = 1,
            customerId = 2,
            professionalId = 3,
            amount = BigDecimal("150.00"),
            subService = "Plumbing",
            image = null,
            subServiceId = 11,
            status = ServiceRequestStatus.ACCEPTED,
            preferredDate = LocalDate.of(2025, 8, 17),
            preferredTime = LocalTime.of(10, 15),
            address = "Main St 1",
            specialInstructions = null,
            createdAt = LocalDateTime.of(2025, 8, 16, 8, 0)
        )

        coEvery { ds.getAcceptedJobs() } returns Result.Success(listOf(dto))

        var list: List<ServiceRequests>? = null
        repo.getAcceptedJobs()
            .onSuccess { list = it }
            .onError { error("Expected success, got error: $it") }

        val r = list!!.single()
        assertEquals(dto.id, r.id)
        assertEquals(dto.customerId, r.customerId)
        assertEquals(dto.professionalId, r.professionalId)
        assertEquals(dto.amount, r.amount)
        assertEquals(dto.subService, r.subService)
        assertEquals(dto.image, r.image)
        assertEquals(dto.subServiceId, r.subServiceId)
        assertEquals(dto.status, r.status)
        assertEquals(dto.preferredDate, r.preferredDate)
        assertEquals(dto.preferredTime, r.preferredTime)
        assertEquals(dto.address, r.address)
        assertEquals(dto.specialInstructions, r.specialInstructions)
        assertEquals(dto.createdAt, r.createdAt)

        coVerify(exactly = 1) { ds.getAcceptedJobs() }
        confirmVerified(ds)
    }

    @Test
    fun `getAcceptedJobs propagates error`() = runTest {
        val err = DataError.Remote.UNKNOWN
        coEvery { ds.getAcceptedJobs() } returns Result.Error(err)

        var hitError = false
        repo.getAcceptedJobs()
            .onSuccess { error("Expected error, got success") }
            .onError { hitError = true }

        assertTrue(hitError)
        coVerify(exactly = 1) { ds.getAcceptedJobs() }
        confirmVerified(ds)
    }

    @Test
    fun `updateJobStatus delegates with correct dto and returns success`() = runTest {
        coEvery {
            ds.updateJobStatus(
                UpdateServiceRequestDto(
                    requestId = 55,
                    newStatus = ServiceRequestStatus.COMPLETED
                )
            )
        } returns Result.Success("Done")

        var msg: String? = null
        repo.updateJobStatus(55, ServiceRequestStatus.COMPLETED)
            .onSuccess { msg = it }
            .onError { error("Expected success, got error: $it") }

        assertEquals("Done", msg)
        coVerify(exactly = 1) {
            ds.updateJobStatus(
                UpdateServiceRequestDto(
                    requestId = 55,
                    newStatus = ServiceRequestStatus.COMPLETED
                )
            )
        }
        confirmVerified(ds)
    }

    @Test
    fun `updateJobStatus propagates error`() = runTest {
        val err = DataError.Remote.REQUEST_TIMEOUT
        coEvery {
            ds.updateJobStatus(
                UpdateServiceRequestDto(
                    requestId = 9,
                    newStatus = ServiceRequestStatus.CANCELLED
                )
            )
        } returns Result.Error(err)

        var hitError = false
        repo.updateJobStatus(9, ServiceRequestStatus.CANCELLED)
            .onSuccess { error("Expected error, got success") }
            .onError { hitError = true }

        assertTrue(hitError)
        coVerify(exactly = 1) {
            ds.updateJobStatus(
                UpdateServiceRequestDto(
                    requestId = 9,
                    newStatus = ServiceRequestStatus.CANCELLED
                )
            )
        }
        confirmVerified(ds)
    }
}
