package com.bakery.network.client.ktor

import com.bakery.network.client.ktor.KtorClient.Companion.addDefaultConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual class KtorClientImpl : KtorClient {
    actual override val coroutineContext: CoroutineContext = Dispatchers.Default

    actual override val client: HttpClient = HttpClient(Js) {
        addDefaultConfig(this)
    }
}
