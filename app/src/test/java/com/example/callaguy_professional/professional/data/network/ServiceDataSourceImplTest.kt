package com.example.callaguy_professional.professional.data.network

import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.core.presentation.AppDefaults
import com.example.callaguy_professional.professional.data.dto.AcceptServiceRequestDto
import com.example.callaguy_professional.professional.data.network.serviceList.ServiceDataSourceImpl
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

class ServiceDataSourceImplTest {

    private fun clientWith(engine: MockEngine) = HttpClient(engine) {

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }


    private val base = AppDefaults.PROFESSIONAL_SERVICE

    @Test
    fun `getServices 200 returns empty list success`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == base) {
                respond(
                    content = "[]",
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val ds = ServiceDataSourceImpl(http)

        var size = -1
        ds.getServices()
            .onSuccess { size = it.size }
            .onError { error("Expected success, got error: $it") }

        assertEquals(0, size)
    }

    @Test
    fun `getServices non-200 maps to error`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == base) {
                respond(
                    content = """{"message":"boom"}""",
                    status = HttpStatusCode.InternalServerError,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val ds = ServiceDataSourceImpl(http)

        var hitError = false
        ds.getServices()
            .onSuccess { error("Expected error, got success size=${it.size}") }
            .onError { hitError = true }

        assertTrue(hitError)
    }

    @Test
    fun `acceptJob 200 returns success string`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == "$base/accept") {
                respond(
                    content = """Accepted Successfully""",
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val ds = ServiceDataSourceImpl(http)

        var msg: String? = null
        ds.acceptJob(AcceptServiceRequestDto(requestId = 42))
            .onSuccess { msg = it }
            .onError { error("Expected success, got error: $it") }

        assertEquals("Accepted Successfully", msg)
    }

    @Test
    fun `acceptJob non-200 maps to error`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == "$base/accept") {
                respond(
                    content = """{"error":"not allowed"}""",
                    status = HttpStatusCode.Forbidden,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val ds = ServiceDataSourceImpl(http)

        var hitError = false
        ds.acceptJob(AcceptServiceRequestDto(requestId = 99))
            .onSuccess { error("Expected error, got success: $it") }
            .onError { hitError = true }

        assertTrue(hitError)
    }
}
