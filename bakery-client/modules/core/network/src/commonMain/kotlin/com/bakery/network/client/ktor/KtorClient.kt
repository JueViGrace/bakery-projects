package com.bakery.network.client.ktor

import com.bakery.network.client.base.NetworkClient
import com.bakery.network.client.ktor.KtorClient.Companion.TIMEOUT
import com.bakery.network.model.ApiOperation
import com.bakery.network.model.NetworkRequestMethod
import com.bakery.network.model.getKtorHttpMethod
import com.bakery.util.Logs
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
* Default ktor client interface.
* */
interface KtorClient : NetworkClient {
    val client: HttpClient

    /**
     * Implementation of call.
     * @param method Network request method.
     * @param urlString Complete path for the request, should not contain prefixes if there is already one created.
     * @param body Request body.
     * @param headers Request headers.
     * @param contentType Request content type.
     * @param serializer Serializer for the response type.
     * */
    override suspend fun <T : Any> call(
        method: NetworkRequestMethod,
        urlString: String,
        body: Any?,
        headers: Map<String, String>,
        contentType: String,
        serializer: KSerializer<T>,
    ): ApiOperation<T> = withContext(coroutineContext) {
        try {
            val response: HttpResponse = client.request {
                this.method = getKtorHttpMethod(method)
                url {
                    setUrl(prefix = prefix, urlString = urlString)
                }
                contentType(ContentType.parse(contentType))
                headers.forEach { (key, value) ->
                    headers {
                        append(key, value)
                    }
                }
                if (body != null) {
                    setBody(body)
                }
            }

            val body: String = response.bodyAsText()

            val responseBody: T = Json.decodeFromString(serializer, body)

            ApiOperation.Success(responseBody)
        } catch (e: Exception) {
            Logs.error(
                tag = this::class.simpleName ?: "KtorClient",
                msg = "Error while making request",
                tr = e,
            )
            coroutineContext.ensureActive()
            ApiOperation.Failure(e)
        }
    }

    companion object {
        /**
         * @property TIMEOUT Timeout for the requests.
         * */
        const val TIMEOUT = 30000L

        /**
         * Adds default configuration to the client.
         * */
        fun <T : HttpClientEngineConfig>addDefaultConfig(config: HttpClientConfig<T>) {
            config.apply {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = TIMEOUT
                    connectTimeoutMillis = TIMEOUT
                    socketTimeoutMillis = TIMEOUT
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
                        },
                    )
                }

                defaultRequest {
                    url(NetworkClient.BASE_URL)
                }
            }
        }

        /**
         * Extension function to set the URL for this client
         * */
        fun URLBuilder.setUrl(prefix: String = "", urlString: String) {
            val url = formatUrl(prefix = prefix, urlString = urlString)

            if (url.contains("?")) {
                val path: List<String> = url.split("?")

                path(path[0])
                var pair: List<String>
                if (path[1].contains("&")) {
                    path[1].split("&").forEach { parameter ->
                        pair = parameter.split("=")
                        parameters.append(pair[0], pair[1])
                    }
                } else {
                    pair = path[1].split("=")
                    parameters.append(pair[0], pair[1])
                }
            } else {
                path(url)
            }
        }

        /**
         * Formats the url for this client.
         * */
        @Throws(IllegalArgumentException::class)
        private fun formatUrl(prefix: String = "", urlString: String): String {
            require(urlString.isNotEmpty())

            var url = ""

            if (prefix.isNotEmpty()) {
                url += if (prefix.startsWith("/")) {
                    prefix
                } else {
                    "/$prefix"
                }
            }

            val path = if (urlString.startsWith("/")) {
                urlString
            } else {
                "/$urlString"
            }

            url += path

            return url
        }
    }
}
