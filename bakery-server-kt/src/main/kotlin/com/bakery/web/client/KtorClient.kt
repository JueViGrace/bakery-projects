package com.bakery.web.client

import com.bakery.web.client.ApiOperation.Failure
import com.bakery.web.client.ApiOperation.Success
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.http.toHttpDateString
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

object KtorClient {
    private lateinit var BASE_URL: String
    private var env: Boolean = false

    fun Application.configureClient() {
        BASE_URL = environment.config.property("ktor.deployment.domain").getString()
        env = environment.config.property("ktor.development").getString().toBoolean()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun client() = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = if (env) {
                LogLevel.ALL
            } else {
                LogLevel.INFO
            }

            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    explicitNulls = true
                }
            )
        }

        install(DefaultRequest) {
            url(BASE_URL)
        }
    }

    inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            Success(data = apiCall())
        } catch (e: Exception) {
            Failure()
        }
    }
}

@Serializable
sealed interface ApiOperation<out T> {
    @Serializable
    data class Success<T>(
        val data: T,
    ) : ApiOperation<T>

    @Serializable
    data class Failure(
        val status: Int = 500,
        val description: String = "Internal server error",
        val time: String = LocalDateTime.now().toHttpDateString(),
        val message: String? = null,
        val error: String? = "Unknown error",
        val path: String? = null,
    ) : ApiOperation<Nothing>
}

@Serializable
data class ApiResponse<T>(
    val status: Int,
    val description: String,
    val body: T? = null,
    val message: String? = null,
    val time: String? = null,
    val error: String? = null,
    val path: String? = null,
)
