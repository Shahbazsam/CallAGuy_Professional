package com.example.callaguy_professional.professional.data.network.profile

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.example.callaguy_professional.core.data.safeCall
import com.example.callaguy_professional.core.domain.DataError
import com.example.callaguy_professional.core.domain.Result
import com.example.callaguy_professional.core.presentation.AppDefaults
import com.example.callaguy_professional.professional.data.dto.ProfessionalProfileInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.util.UUID

class ProfileDataSourceImpl(
    private val httpClient: HttpClient
) : ProfileDataSource {

    override suspend fun uploadProfilePicture(
        uri: Uri,
        context: Context
    ): Result<String, DataError.Remote> {
        return safeCall<String> {

            val contentResolver = context.contentResolver

            val bytes = contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            } ?: byteArrayOf()

            val mimeType = contentResolver.getType(uri) ?: "image/jpeg"

            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
            val fileName = "${UUID.randomUUID()}.$extension"


            httpClient.submitFormWithBinaryData(
                url = AppDefaults.PROFESSIONAL_PROFILE_PICTURE,
                formData = formData {
                    append(
                        key = "image",
                        value = bytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentDisposition, "form-data; name=\"image\"; filename=\"$fileName.jpg\"")
                            append(HttpHeaders.ContentType, mimeType)
                        }
                    )
                }
            )
        }
    }

    override suspend fun getProfileInfo(): Result<ProfessionalProfileInfoDto, DataError.Remote> {
        return safeCall<ProfessionalProfileInfoDto> {
            httpClient.get(
                urlString = "${AppDefaults.PROFESSIONAL_PROFILE_PICTURE}/profile_info"
            )
        }
    }
}
