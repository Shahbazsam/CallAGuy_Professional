package com.example.callaguy_professional.core.data

import com.example.callaguy_professional.core.Serialization.BigDecimalSerializer
import com.example.callaguy_professional.core.Serialization.LocalDateSerializer
import com.example.callaguy_professional.core.Serialization.LocalDateTimeSerializer
import com.example.callaguy_professional.core.Serialization.LocalTimeSerializer
import com.example.callaguy_professional.core.auth.TokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object HttpClientFactory {
    fun create( tokenProvider: TokenProvider) : HttpClient {
        val json = Json {
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                contextual(LocalDate::class , LocalDateSerializer)
                contextual(LocalTime::class , LocalTimeSerializer)
                contextual(LocalDateTime::class , LocalDateTimeSerializer)
                contextual(BigDecimal::class , BigDecimalSerializer)

            }
        }

        return HttpClient(OkHttp){
            install(ContentNegotiation){
                json(json)
            }
            install(Logging){
                logger = object : Logger{
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)

                url {
                    protocol = URLProtocol.HTTPS
                    host = "10.20.6.60"
                    port = 8081
                }
                tokenProvider.getToken()?.let { token ->
                    headers.append(HttpHeaders.Authorization , "Bearer $token")
                }
            }
        }
    }
}