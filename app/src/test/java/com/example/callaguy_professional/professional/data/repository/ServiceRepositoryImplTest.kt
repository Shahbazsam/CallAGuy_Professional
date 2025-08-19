package com.example.callaguy_professional.professional.data.repository

import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.professional.data.dto.AcceptServiceRequestDto
import com.example.callaguy_professional.professional.data.dto.ServiceRequestDto
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.data.network.serviceList.ServiceDataSource
import com.example.callaguy_professional.professional.domain.ServiceRepository
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


class ServiceRepoImplTest {

    private lateinit var ds: ServiceDataSource
    private lateinit var repo: ServiceRepository

    @Before
    fun setup() {
        ds = mockk()
        repo = ServiceRepoImpl(ds)
    }

    @Test
    fun `getServiceList maps dto to domain`() = runTest {
        val dto = ServiceRequestDto(
            id = 7,
            customerId = 10,
            professionalId = null,
            amount = BigDecimal("99.50"),
            subService = "AC Repair",
            image = "http://img",
            subServiceId = 5,
            status = ServiceRequestStatus.REQUESTED,
            preferredDate = LocalDate.of(2025, 8, 17),
            preferredTime = LocalTime.of(14, 30),
            address = "Some Street 123",
            specialInstructions = "Be careful",
            createdAt = LocalDateTime.of(2025, 8, 16, 12, 0)
        )

        coEvery { ds.getServices() } returns Result.Success(listOf(dto))

        var list: List<ServiceRequests>? = null
        repo.getServiceList()
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
        coVerify(exactly = 1) { ds.getServices() }
        confirmVerified(ds)
    }

    @Test
    fun `getServiceList propagates error`() = runTest {
        val err = DataError.Remote.UNKNOWN
        coEvery { ds.getServices() } returns Result.Error(err)

        var hitError = false
        repo.getServiceList()
            .onSuccess { error("Expected error, got success") }
            .onError { hitError = true }

        assertTrue(hitError)
        coVerify(exactly = 1) { ds.getServices() }
        confirmVerified(ds)
    }

    @Test
    fun `onAcceptJob delegates with correct dto and returns success string`() = runTest {
        coEvery { ds.acceptJob(AcceptServiceRequestDto(requestId = 42)) } returns Result.Success("OK")

        var msg: String? = null
        repo.onAcceptJob(42)
            .onSuccess { msg = it }
            .onError { error("Expected success, got error: $it") }

        assertEquals("OK", msg)
        coVerify(exactly = 1) { ds.acceptJob(AcceptServiceRequestDto(requestId = 42)) }
        confirmVerified(ds)
    }

    @Test
    fun `onAcceptJob propagates error`() = runTest {
        val err = DataError.Remote.REQUEST_TIMEOUT
        coEvery { ds.acceptJob(AcceptServiceRequestDto(requestId = 99)) } returns Result.Error(err)

        var hitError = false
        repo.onAcceptJob(99)
            .onSuccess { error("Expected error, got success") }
            .onError { hitError = true }

        assertTrue(hitError)
        coVerify(exactly = 1) { ds.acceptJob(AcceptServiceRequestDto(requestId = 99)) }
        confirmVerified(ds)
    }
}
