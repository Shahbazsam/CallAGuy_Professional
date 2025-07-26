package com.example.callaguy_professional.professional.presentation.profile

import android.content.Context
import android.net.Uri

sealed interface ProfileAction {
    data class OnProfileImageChanged (val image : Uri, val context : Context) : ProfileAction
    object OnLogOutClick : ProfileAction
    object ResetSuccess : ProfileAction
    object Retry : ProfileAction
}