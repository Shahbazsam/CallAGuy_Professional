package com.example.callaguy_professional.professional.data.network

import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.core.presentation.AppDefaults
import com.example.callaguy_professional.professional.data.dto.LoginRequestDto
import com.example.callaguy_professional.professional.data.dto.LoginResponseDto
import com.example.callaguy_professional.professional.data.network.auth.AuthUseCaseImpl
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

class AuthUseCaseImplTest {

    private fun clientWith(engine: MockEngine) = HttpClient(engine) {
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    }

    private val base = AppDefaults.PROFESSIONAL_AUTH

    @Test
    fun `login success returns Result_Success with token`() = runTest {
        val token = "fake.jwt.token"

        val engine = MockEngine { request ->
            if (request.url.encodedPath == "$base/login") {
                val bodyJson = Json.encodeToString(LoginResponseDto(token = token))
                respond(
                    content = bodyJson,
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val useCase = AuthUseCaseImpl(http)

        val result = useCase.login(LoginRequestDto(email = "pro@dev.com", password = "Secret123"))

        var received: String? = null
        result.onSuccess { r -> received = r.token }
        result.onError { error("Expected success, got error: $it") }

        assertEquals(token, received)
    }

    @Test
    fun `login non-200 maps to Result_Error`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == "$base/login") {
                respond(
                    content = """{"message":"unauthorized"}""",
                    status = HttpStatusCode.Unauthorized,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val useCase = AuthUseCaseImpl(http)

        val result = useCase.login(LoginRequestDto(email = "pro@dev.com", password = "badpass"))

        var hitError = false
        result.onSuccess { error("Expected error, got success with ${it.token}") }
        result.onError { hitError = true }

        assertTrue(hitError)
    }

    @Test
    fun `login malformed JSON maps to Result_Error`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == "$base/login") {
                respond(
                    content = """{"token":123}""",
                    status = HttpStatusCode.BadRequest,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val useCase = AuthUseCaseImpl(http)

        var hitError = false
        useCase.login(LoginRequestDto("a@b.com", "x"))
            .onSuccess { error("Expected error, got success") }
            .onError { hitError = true }

        assertTrue(hitError)
    }

    @Test
    fun `isApproved success true`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == "$base/is_approved") {
                respond(
                    content = "true",
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val useCase = AuthUseCaseImpl(http)

        var value: Boolean? = null
        useCase.isApproved()
            .onSuccess { value = it }
            .onError { error("Expected success, got error: $it") }

        assertEquals(true, value)
    }

    @Test
    fun `isApproved success false`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == "$base/is_approved") {
                respond(
                    content = "false",
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val useCase = AuthUseCaseImpl(http)

        var value: Boolean? = null
        useCase.isApproved()
            .onSuccess { value = it }
            .onError { error("Expected success, got error: $it") }

        assertEquals(false, value)
    }

    @Test
    fun `isApproved non-200 maps to Result_Error`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == "$base/is_approved") {
                respond(
                    content = """{"error":"server"}""",
                    status = HttpStatusCode.InternalServerError,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }

        val http = clientWith(engine)
        val useCase = AuthUseCaseImpl(http)

        var hitError = false
        useCase.isApproved()
            .onSuccess { error("Expected error, got success: $it") }
            .onError { hitError = true }

        assertTrue(hitError)
    }
}
