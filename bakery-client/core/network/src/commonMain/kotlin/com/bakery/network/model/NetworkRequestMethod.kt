package com.bakery.network.model

import io.ktor.http.HttpMethod

/**
* Project specific network request methods.
* */
enum class NetworkRequestMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
    OPTIONS,
}

/**
* Returns the ktor http method for the given network request method.
* @param method Network request method.
* */
fun getKtorHttpMethod(method: NetworkRequestMethod): HttpMethod = when (method) {
    NetworkRequestMethod.GET -> HttpMethod.Get
    NetworkRequestMethod.POST -> HttpMethod.Post
    NetworkRequestMethod.PUT -> HttpMethod.Put
    NetworkRequestMethod.DELETE -> HttpMethod.Delete
    NetworkRequestMethod.PATCH -> HttpMethod.Patch
    NetworkRequestMethod.HEAD -> HttpMethod.Head
    NetworkRequestMethod.OPTIONS -> HttpMethod.Options
}
