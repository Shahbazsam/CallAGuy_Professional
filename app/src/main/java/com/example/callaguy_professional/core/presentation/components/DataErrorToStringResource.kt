package com.example.callaguy_professional.core.presentation.components

import com.example.callaguy_professional.R
import com.example.callaguy_professional.core.domain.DataError

fun DataError.toUiText(): UiText {
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> R.string.error_disk_full
        DataError.Local.UNKNOWN -> R.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> R.string.error_no_internet
        DataError.Remote.SERVER_ERROR -> R.string.error_unknown
        DataError.Remote.SERIALIZATION -> R.string.error_serialization
        DataError.Remote.UNKNOWN -> R.string.error_unknown
    }
    return UiText.StringResourceId(stringRes)
}