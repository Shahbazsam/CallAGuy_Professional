package com.example.callaguy_professional.professional.data.repository

import android.content.Context
import android.net.Uri
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.domain.onError
import com.example.callaguy_professional.core.domain.onSuccess
import com.example.callaguy_professional.professional.data.dto.ProfessionalProfileInfoDto
import com.example.callaguy_professional.professional.data.network.profile.ProfileDataSource
import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo
import com.example.callaguy_professional.professional.domain.ProfileRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProfileRepositoryImplTest {

    private lateinit var ds: ProfileDataSource
    private lateinit var repo: ProfileRepository

    private val context: Context = mockk(relaxed = true)
    private val uri: Uri = mockk(relaxed = true)

    @Before
    fun setup() {
        ds = mockk()
        repo = ProfileRepositoryImpl(ds)
    }

    @Test
    fun `uploadProfilePicture delegates and returns success string`() = runTest {
        coEvery { ds.uploadProfilePicture(uri, context) } returns Result.Success("OK")

        var msg: String? = null
        repo.uploadProfilePicture(uri, context)
            .onSuccess { msg = it }
            .onError { error("Expected success, got error: $it") }

        assertEquals("OK", msg)
        coVerify(exactly = 1) { ds.uploadProfilePicture(uri, context) }
        confirmVerified(ds)
    }

    @Test
    fun `uploadProfilePicture propagates error`() = runTest {
        val err = DataError.Remote.REQUEST_TIMEOUT
        coEvery { ds.uploadProfilePicture(uri, context) } returns Result.Error(err)

        var hitError = false
        repo.uploadProfilePicture(uri, context)
            .onSuccess { error("Expected error") }
            .onError { hitError = true }

        assertTrue(hitError)
        coVerify(exactly = 1) { ds.uploadProfilePicture(uri, context) }
        confirmVerified(ds)
    }

    @Test
    fun `getProfileInfo maps dto to domain`() = runTest {
        val dto = ProfessionalProfileInfoDto(
            userName = "Pro Dev",
            email = "pro@dev.com",
            experience = 4,
            isApproved = true,
            profilePicture = "http://img",
            services = listOf("AC Repair", "Cleaning")
        )
        coEvery { ds.getProfileInfo() } returns Result.Success(dto)

        var domain: ProfessionalProfileInfo? = null
        repo.getProfileInfo()
            .onSuccess { domain = it }
            .onError { error("Expected success, got error: $it") }

        val r = domain!!
        assertEquals(dto.userName, r.userName)
        assertEquals(dto.email, r.email)
        assertEquals(dto.experience, r.experience)
        assertEquals(dto.isApproved, r.isApproved)
        assertEquals(dto.profilePicture, r.profilePicture)
        assertEquals(dto.services, r.services)

        coVerify(exactly = 1) { ds.getProfileInfo() }
        confirmVerified(ds)
    }

    @Test
    fun `getProfileInfo propagates error`() = runTest {
        val err = DataError.Remote.UNKNOWN
        coEvery { ds.getProfileInfo() } returns Result.Error(err)

        var hitError = false
        repo.getProfileInfo()
            .onSuccess { error("Expected error") }
            .onError { hitError = true }

        assertTrue(hitError)
        coVerify(exactly = 1) { ds.getProfileInfo() }
        confirmVerified(ds)
    }
}
