package com.bakery.network.client.ktor

import io.ktor.client.HttpClient

/**
* Ktor client expected implementation.
* */
expect class KtorClientImpl(
    baseUrl: String,
) : KtorClient {
    override val baseUrl: String
    override fun client(baseUrl: String?): HttpClient
}
