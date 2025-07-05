package com.bakery.network.client.ktor

import com.bakery.network.client.base.NetworkClient
import com.bakery.network.model.ApiOperation
import com.bakery.network.model.NetworkRequestMethod
import com.bakery.network.model.getKtorHttpMethod
import com.bakery.util.Logs
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
* Default ktor client interface.
* */
interface KtorClient : NetworkClient {
    /*
     * todo:
     *     i think this creates a new client every time
     *     it could be good to have a property for this
     *     instead of creating a new client every time
     */
    /**
     *  Creates http client.
     *  @param baseUrl Base url for the request.
     * */
    fun client(baseUrl: String? = null): HttpClient

    /**
     * Implementation of call.
     * @param method Network request method.
     * @param baseUrl Base url for the request.
     * @param urlString Complete path for the request, should not contain prefixes if there is already one created.
     * @param body Request body.
     * @param headers Request headers.
     * @param contentType Request content type.
     * */
    override suspend fun <T : Any> call(
        method: NetworkRequestMethod,
        baseUrl: String?,
        urlString: String,
        body: Any?,
        headers: Map<String, String>,
        contentType: String,
        serializer: KSerializer<T>,
    ): ApiOperation<T> {
        return try {
            val response: HttpResponse = client(baseUrl).request {
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
                tr = e
            )
            coroutineContext.ensureActive()
            ApiOperation.Failure(e)
        }
    }

    companion object {
        /**
         * Timeout for the requests.
         * */
        const val TIMEOUT = 30000L

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
