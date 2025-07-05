package com.bakery.network.client.base

import com.bakery.network.client.base.NetworkClient.Companion.DEFAULT_CONTENT_TYPE
import com.bakery.network.model.ApiOperation
import com.bakery.network.model.NetworkRequestMethod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.coroutines.CoroutineContext

/**
* Default network client interface.
* */
interface NetworkClient {
    val baseUrl: String

    // todo: having prefix set up like this could be troublesome
    val prefix: String
        get() = ""

    // You can remove this properties if you don't need them
    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    val scope: CoroutineScope
        get() = CoroutineScope(coroutineContext + SupervisorJob())

    /**
     * Performs a network request.
     * @param method Network request method.
     * @param baseUrl Base url for the request.
     * @param urlString Complete path for the request, should not contain prefixes if there is already one created.
     * @param body Request body.
     * @param headers Request headers.
     * @param contentType Request content type.
     * */
    suspend fun <T : Any> call(
        method: NetworkRequestMethod,
        baseUrl: String?,
        urlString: String,
        body: Any? = null,
        headers: Map<String, String>,
        contentType: String,
        serializer: KSerializer<T>,
    ): ApiOperation<T>

    companion object {
        /**
         * @property DEFAULT_CONTENT_TYPE Default content type for the request.
         * */
        const val DEFAULT_CONTENT_TYPE = "application/json"
    }
}

/**
 * Performs a GET request.
 * @param baseUrl Base url for the request.
 * @param urlString Complete path for the request.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.get(
    baseUrl: String? = null,
    urlString: String,
    headers: Map<String, String> = emptyMap(),
    contentType: String = NetworkClient.Companion.DEFAULT_CONTENT_TYPE,
): ApiOperation<T> {
    return call(
        method = NetworkRequestMethod.GET,
        baseUrl = baseUrl,
        urlString = urlString,
        body = null,
        headers = headers,
        contentType = contentType,
        serializer = serializer<T>()
    )
}

/**
 * Performs a POST request.
 * @param baseUrl Base url for the request.
 * @param urlString Complete path for the request.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.post(
    baseUrl: String? = null,
    urlString: String,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    contentType: String = NetworkClient.Companion.DEFAULT_CONTENT_TYPE,
): ApiOperation<T> {
    return call(
        method = NetworkRequestMethod.POST,
        baseUrl = baseUrl,
        urlString = urlString,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer<T>()
    )
}

/**
 * Performs a PUT request.
 * @param baseUrl Base url for the request.
 * @param urlString Complete path for the request.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.put(
    baseUrl: String? = null,
    urlString: String,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    contentType: String = NetworkClient.Companion.DEFAULT_CONTENT_TYPE,
): ApiOperation<T> {
    return call(
        method = NetworkRequestMethod.PUT,
        baseUrl = baseUrl,
        urlString = urlString,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer<T>()
    )
}

/**
 * Performs a DELETE request.
 * @param baseUrl Base url for the request.
 * @param urlString Complete path for the request.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.delete(
    baseUrl: String? = null,
    urlString: String,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    contentType: String = NetworkClient.Companion.DEFAULT_CONTENT_TYPE,
): ApiOperation<T> {
    return call(
        method = NetworkRequestMethod.DELETE,
        baseUrl = baseUrl,
        urlString = urlString,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer<T>()
    )
}

/**
 * Performs a PATCH request.
 * @param baseUrl Base url for the request.
 * @param urlString Complete path for the request.
 * @param headers Request headers.
 * @param contentType Request content type.
 * */
suspend inline fun <reified T : Any> NetworkClient.patch(
    baseUrl: String? = null,
    urlString: String,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    contentType: String = NetworkClient.Companion.DEFAULT_CONTENT_TYPE,
): ApiOperation<T> {
    return call(
        method = NetworkRequestMethod.PATCH,
        baseUrl = baseUrl,
        urlString = urlString,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer<T>()
    )
}
