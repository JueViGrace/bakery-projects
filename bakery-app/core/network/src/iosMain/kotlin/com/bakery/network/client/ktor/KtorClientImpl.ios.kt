package com.bakery.network.client.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import com.bakery.util.Logs

/**
* Ktor client Native implementation.
* @param baseUrl Base url for the client.
* */
actual class KtorClientImpl actual constructor(
    actual override val baseUrl: String,
) : KtorClient {
    actual override fun client(baseUrl: String?): HttpClient {
        return HttpClient(Darwin) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = KtorClient.TIMEOUT
                connectTimeoutMillis = KtorClient.TIMEOUT
                socketTimeoutMillis = KtorClient.TIMEOUT
            }

            install(ResponseObserver) {
                onResponse { response ->
                    val tag = this::class.simpleName ?: "KtorClientImpl"
                    Logs.info(tag = tag, msg = "HTTP response: $response")
                    Logs.info(tag = tag, msg = "HTTP body: ${response.body<Any>()}")
                    Logs.info(tag = tag, msg = "HTTP status: ${response.status.value}")
                    Logs.info(tag = tag, msg = "HTTP description: ${response.status.description}")
                }
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

            defaultRequest {
                url(baseUrl ?: this@KtorClientImpl.baseUrl)
            }
        }
    }
}
