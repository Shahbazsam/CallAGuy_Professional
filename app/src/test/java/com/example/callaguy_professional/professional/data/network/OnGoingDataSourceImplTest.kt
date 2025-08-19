package com.example.callaguy_professional.professional.data.network

import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.core.presentation.AppDefaults
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.data.dto.UpdateServiceRequestDto
import com.example.callaguy_professional.professional.data.network.onGoing.OnGoingDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class OnGoingDataSourceImplTest {

    private fun clientWith(engine: MockEngine) = HttpClient(engine) {
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    }

    private val base = AppDefaults.PROFESSIONAL_SERVICE

    @Test
    fun `getAcceptedJobs 200 returns empty list`() = runTest {
        val engine = MockEngine { req ->
            if (req.url.encodedPath == "$base/my_orders") {
                respond(
                    content = "[]",
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${req.url}")
        }

        val http = clientWith(engine)
        val ds = OnGoingDataSourceImpl(http)

        var size = -1
        ds.getAcceptedJobs()
            .onSuccess { size = it.size }
            .onError { error("Expected success, got error: $it") }

        assertEquals(0, size)
    }

    @Test
    fun `getAcceptedJobs non-200 maps to error`() = runTest {
        val engine = MockEngine { req ->
            if (req.url.encodedPath == "$base/my_orders") {
                respond(
                    content = """{"message":"server down"}""",
                    status = HttpStatusCode.ServiceUnavailable,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${req.url}")
        }

        val http = clientWith(engine)
        val ds = OnGoingDataSourceImpl(http)

        var hitError = false
        ds.getAcceptedJobs()
            .onSuccess { error("Expected error, got success") }
            .onError { hitError = true }

        assertTrue(hitError)
    }

    @Test
    fun `updateJobStatus 200 returns message`() = runTest {
        val engine = MockEngine { req ->
            if (req.url.encodedPath == "$base/update_status") {
                respond(
                    content = """Status updated""", // JSON string
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${req.url}")
        }

        val http = clientWith(engine)
        val ds = OnGoingDataSourceImpl(http)

        var msg: String? = null
        ds.updateJobStatus(
            UpdateServiceRequestDto(
                requestId = 77,
                newStatus = ServiceRequestStatus.ACCEPTED
            )
        )
            .onSuccess { msg = it }
            .onError { error("Expected success, got error: $it") }

        assertEquals("Status updated", msg)
    }

    @Test
    fun `updateJobStatus non-200 maps to error`() = runTest {
        val engine = MockEngine { req ->
            if (req.url.encodedPath == "$base/update_status") {
                respond(
                    content = """{"error":"forbidden"}""",
                    status = HttpStatusCode.Forbidden,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${req.url}")
        }

        val http = clientWith(engine)
        val ds = OnGoingDataSourceImpl(http)

        var hitError = false
        ds.updateJobStatus(
            UpdateServiceRequestDto(
                requestId = 9,
                newStatus = ServiceRequestStatus.CANCELLED
            )
        )
            .onSuccess { error("Expected error, got success") }
            .onError { hitError = true }

        assertTrue(hitError)
    }
}
