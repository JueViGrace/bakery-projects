package com.bakery.network.client.ktor

import io.ktor.client.HttpClient
import kotlin.coroutines.CoroutineContext

/**
* Ktor client expected implementation.
* */
expect class KtorClientImpl() : KtorClient {
    override val coroutineContext: CoroutineContext
    override val client: HttpClient
}
