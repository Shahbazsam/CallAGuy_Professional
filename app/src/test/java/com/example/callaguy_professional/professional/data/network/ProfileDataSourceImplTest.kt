package com.example.callaguy_professional.professional.data.network


import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.core.presentation.AppDefaults
import com.example.callaguy_professional.professional.data.dto.ProfessionalProfileInfoDto
import com.example.callaguy_professional.professional.data.network.profile.ProfileDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.ByteArrayInputStream

class ProfileDataSourceImplTest {

    private fun clientWith(engine: MockEngine) = HttpClient(engine) {
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    }

    @Test
    fun `uploadProfilePicture non-200 maps to error`() = runTest {
        val engine = MockEngine { request ->
            if (request.url.encodedPath == AppDefaults.PROFESSIONAL_PROFILE_PICTURE) {
                respond(
                    content = """{"message":"invalid"}""",
                    status = HttpStatusCode.BadRequest,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${request.url}")
        }
        val http = clientWith(engine)
        val ds = ProfileDataSourceImpl(http)

        val ctx = mockk<Context>()
        val resolver = mockk<ContentResolver>()
        val uri = mockk<Uri>()

        every { ctx.contentResolver } returns resolver
        every { resolver.openInputStream(uri) } returns ByteArrayInputStream(byteArrayOf(1, 2, 3))
        every { resolver.getType(uri) } returns "image/jpeg"

        var hitError = false
        ds.uploadProfilePicture(uri, ctx)
            .onSuccess { error("Expected error") }
            .onError { hitError = true }

        assertTrue(hitError)
    }

    @Test
    fun `getProfileInfo 200 returns dto`() = runTest {
        val dto = ProfessionalProfileInfoDto(
            userName = "Pro Dev",
            email = "pro@dev.com",
            experience = 5,
            isApproved = true,
            profilePicture = "https://cdn/img.jpg",
            services = listOf("AC Repair", "Cleaning")
        )

        val engine = MockEngine { req ->
            if (req.url.encodedPath == "${AppDefaults.PROFESSIONAL_PROFILE_PICTURE}/profile_info") {
                respond(
                    content = Json.encodeToString(dto),
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${req.url}")
        }

        val http = clientWith(engine)
        val ds = ProfileDataSourceImpl(http)

        var received: ProfessionalProfileInfoDto? = null
        ds.getProfileInfo()
            .onSuccess { received = it }
            .onError { error("Expected success, got error: $it") }

        val r = received!!
        assertEquals(dto.userName, r.userName)
        assertEquals(dto.email, r.email)
        assertEquals(dto.experience, r.experience)
        assertEquals(dto.isApproved, r.isApproved)
        assertEquals(dto.profilePicture, r.profilePicture)
        assertEquals(dto.services, r.services)
    }

    @Test
    fun `getProfileInfo non-200 maps to error`() = runTest {
        val engine = MockEngine { req ->
            if (req.url.encodedPath == "${AppDefaults.PROFESSIONAL_PROFILE_PICTURE}/profile_info") {
                respond(
                    content = """{"message":"unauthorized"}""",
                    status = HttpStatusCode.Unauthorized,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json.toString()
                    )
                )
            } else error("Unexpected ${req.url}")
        }

        val http = clientWith(engine)
        val ds = ProfileDataSourceImpl(http)

        var hitError = false
        ds.getProfileInfo()
            .onSuccess { error("Expected error, got success: $it") }
            .onError { hitError = true }

        assertTrue(hitError)
    }
}
