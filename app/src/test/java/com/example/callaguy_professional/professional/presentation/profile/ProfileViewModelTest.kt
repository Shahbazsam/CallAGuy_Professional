package com.example.callaguy_professional.professional.presentation.profile

import android.content.Context
import android.net.Uri
import app.cash.turbine.test
import com.example.callaguy_professional.MainCoroutineRule
import com.example.callaguy_professional.core.auth.TokenProvider
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo
import com.example.callaguy_professional.professional.domain.ProfileRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    @get:Rule
    val mainRule = MainCoroutineRule()

    private lateinit var tokenProvider: TokenProvider
    private lateinit var repository: ProfileRepository
    private lateinit var context: Context
    private lateinit var imageUri: Uri

    @Before
    fun setup() {
        tokenProvider = mockk()
        repository = mockk()
        context = mockk()
        imageUri = mockk()

        every { tokenProvider.clearToken() } just Runs
        every { context.getString(any()) } returns "Profile update failed, try again"
    }

    @Test
    fun `init loads profile info - success`() = runTest {
        val info = ProfessionalProfileInfo(
            userName = "Pro Dev",
            email = "pro@dev.com",
            experience = 4,
            isApproved = true,
            profilePicture = "http://img",
            services = listOf("AC Repair")
        )
        coEvery { repository.getProfileInfo() } returns Result.Success(info)

        val vm = ProfileViewModel(tokenProvider, repository)

        vm.state.test {
            val initial = awaitItem()
            assertFalse(initial.isProfileStateLoading)
            assertNull(initial.info)

            val loading = awaitItem()
            assertTrue(loading.isProfileStateLoading)
            assertNull(loading.profileStateError)

            val done = awaitItem()
            assertFalse(done.isProfileStateLoading)
            assertEquals(info, done.info)
            assertNull(done.profileStateError)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init loads profile info - error`() = runTest {
        coEvery { repository.getProfileInfo() } returns Result.Error(DataError.Remote.REQUEST_TIMEOUT)

        val vm = ProfileViewModel(tokenProvider, repository)

        vm.state.test {
            awaitItem() // initial
            val loading = awaitItem()
            assertTrue(loading.isProfileStateLoading)

            val done = awaitItem()
            assertFalse(done.isProfileStateLoading)
            assertNull(done.info)
            assertNotNull(done.profileStateError)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnProfileImageChanged - success sets isSuccess true and clears profileUpdateError`() =
        runTest {
            coEvery { repository.getProfileInfo() } returns Result.Success(
                ProfessionalProfileInfo("n", "e", 0, false, null, emptyList())
            )
            coEvery {
                repository.uploadProfilePicture(
                    imageUri,
                    context
                )
            } returns Result.Success("OK")

            val vm = ProfileViewModel(tokenProvider, repository)

            vm.state.test {
                awaitItem(); awaitItem(); awaitItem()

                vm.onAction(ProfileAction.OnProfileImageChanged(imageUri, context))

                val uploading = awaitItem()
                assertTrue(uploading.isLoading)
                assertNull(uploading.profileUpdateError)

                val after = awaitItem()
                assertFalse(after.isLoading)
                assertTrue(after.isSuccess)
                assertNull(after.profileUpdateError)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `OnProfileImageChanged - error sets profileUpdateError from context#getString`() = runTest {
        coEvery { repository.getProfileInfo() } returns Result.Success(
            ProfessionalProfileInfo("n", "e", 0, false, null, emptyList())
        )
        coEvery { repository.uploadProfilePicture(imageUri, context) } returns Result.Error(
            DataError.Remote.UNKNOWN
        )
        every { context.getString(any()) } returns "Profile update failed, try again"

        val vm = ProfileViewModel(tokenProvider, repository)

        vm.state.test {
            awaitItem(); awaitItem(); awaitItem()

            vm.onAction(ProfileAction.OnProfileImageChanged(imageUri, context))

            val uploading = awaitItem()
            assertTrue(uploading.isLoading)

            val after = awaitItem()
            assertFalse(after.isLoading)
            assertEquals("Profile update failed, try again", after.profileUpdateError)
            assertFalse(after.isSuccess)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `ResetSuccess turns isSuccess to false`() = runTest {
        coEvery { repository.getProfileInfo() } returns Result.Success(
            ProfessionalProfileInfo("n", "e", 0, false, null, emptyList())
        )
        coEvery { repository.uploadProfilePicture(imageUri, context) } returns Result.Success("OK")

        val vm = ProfileViewModel(tokenProvider, repository)

        vm.state.test {
            awaitItem(); awaitItem(); awaitItem()

            vm.onAction(ProfileAction.OnProfileImageChanged(imageUri, context))
            awaitItem()
            val success = awaitItem()
            assertTrue(success.isSuccess)

            vm.onAction(ProfileAction.ResetSuccess)
            val reset = awaitItem()
            assertFalse(reset.isSuccess)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Retry triggers loading then final from getProfileInfo`() = runTest {
        coEvery { repository.getProfileInfo() } returnsMany listOf(
            Result.Error(DataError.Remote.REQUEST_TIMEOUT),
            Result.Success(ProfessionalProfileInfo("n", "e", 0, false, null, emptyList()))
        )

        val vm = ProfileViewModel(tokenProvider, repository)

        vm.state.test {
            awaitItem()
            awaitItem()
            val initDone = awaitItem()
            assertNotNull(initDone.profileStateError)

            vm.onAction(ProfileAction.Retry)

            val loading = awaitItem()
            assertTrue(loading.isProfileStateLoading)

            val done = awaitItem()
            assertFalse(done.isProfileStateLoading)
            assertNotNull(done.info)
            assertNull(done.profileStateError)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnLogOutClick clears token`() = runTest {
        coEvery { repository.getProfileInfo() } returns Result.Success(
            ProfessionalProfileInfo("n", "e", 0, false, null, emptyList())
        )
        val vm = ProfileViewModel(tokenProvider, repository)

        vm.state.test {
            awaitItem(); awaitItem(); awaitItem()
            cancelAndIgnoreRemainingEvents()
        }

        vm.onAction(ProfileAction.OnLogOutClick)
        verify(exactly = 1) { tokenProvider.clearToken() }
    }
}
