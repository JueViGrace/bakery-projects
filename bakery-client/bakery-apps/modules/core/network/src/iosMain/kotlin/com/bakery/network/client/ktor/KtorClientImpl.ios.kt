package com.bakery.network.client.ktor

import com.bakery.network.client.ktor.KtorClient.Companion.addDefaultConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual class KtorClientImpl : KtorClient {
    actual override val coroutineContext: CoroutineContext = Dispatchers.IO

    actual override val client: HttpClient = HttpClient(Darwin) {
        addDefaultConfig(this)
    }
}
