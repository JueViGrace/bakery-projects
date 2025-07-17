package com.bakery.network.client.base

import com.bakery.network.client.base.NetworkClient.Companion.BASE_URL
import com.bakery.network.client.base.NetworkClient.Companion.DEFAULT_API_PREFIX
import com.bakery.network.client.base.NetworkClient.Companion.DEFAULT_CONTENT_TYPE
import com.bakery.network.model.ApiOperation
import com.bakery.network.model.NetworkRequestMethod
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

/**
* Default network client interface.
* */
interface NetworkClient {
    // todo: having prefix set up like this could be troublesome
    val prefix: String
        get() = ""

    // You can remove this properties if you don't need them
    val coroutineContext: CoroutineContext
    val scope: CoroutineScope
        get() = CoroutineScope(coroutineContext + SupervisorJob())

    /**
     * Performs a network request.
     * @param method Network request method.
     * @param urlString Complete path for the request, should not contain prefixes if there is already one created.
     * @param body Request body.
     * @param headers Request headers.
     * @param contentType Request content type.
     * @param serializer Serializer for the response type.
     * */
    suspend fun <T : Any> call(
        method: NetworkRequestMethod,
        urlString: String,
        body: Any? = null,
        headers: Map<String, String>,
        contentType: String,
        serializer: KSerializer<T>,
    ): ApiOperation<T>

    companion object {
        /**
         * @property BASE_URL Server base url for the client.
         * */
        const val BASE_URL: String = "http://192.168.0.235:5000/"

        /**
         * @property DEFAULT_API_PREFIX Default api prefix for the client.
         * */
        const val DEFAULT_API_PREFIX: String = "/api"

        /**
         * @property DEFAULT_CONTENT_TYPE Default content type for the request.
         * */
        const val DEFAULT_CONTENT_TYPE = "application/json"
    }
}

/**
 * Performs a GET request.
 * @param urlString Complete path for the request.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.get(
    urlString: String,
    headers: Map<String, String> = emptyMap(),
    contentType: String = DEFAULT_CONTENT_TYPE,
): ApiOperation<T> = call(
    method = NetworkRequestMethod.GET,
    urlString = urlString,
    body = null,
    headers = headers,
    contentType = contentType,
    serializer = serializer<T>(),
)

/**
 * Performs a POST request.
 * @param urlString Complete path for the request.
 * @param body Request body.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.post(
    urlString: String,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    contentType: String = DEFAULT_CONTENT_TYPE,
): ApiOperation<T> = call(
    method = NetworkRequestMethod.POST,
    urlString = urlString,
    body = body,
    headers = headers,
    contentType = contentType,
    serializer = serializer<T>(),
)

/**
 * Performs a PUT request.
 * @param urlString Complete path for the request.
 * @param body Request body.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.put(
    urlString: String,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    contentType: String = DEFAULT_CONTENT_TYPE,
): ApiOperation<T> = call(
    method = NetworkRequestMethod.PUT,
    urlString = urlString,
    body = body,
    headers = headers,
    contentType = contentType,
    serializer = serializer<T>(),
)

/**
 * Performs a DELETE request.
 * @param urlString Complete path for the request.
 * @param body Request body.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.delete(
    urlString: String,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    contentType: String = DEFAULT_CONTENT_TYPE,
): ApiOperation<T> = call(
    method = NetworkRequestMethod.DELETE,
    urlString = urlString,
    body = body,
    headers = headers,
    contentType = contentType,
    serializer = serializer<T>(),
)

/**
 * Performs a PATCH request.
 * @param urlString Complete path for the request.
 * @param body Request body.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.patch(
    urlString: String,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    contentType: String = DEFAULT_CONTENT_TYPE,
): ApiOperation<T> = call(
    method = NetworkRequestMethod.PATCH,
    urlString = urlString,
    body = body,
    headers = headers,
    contentType = contentType,
    serializer = serializer<T>(),
)
