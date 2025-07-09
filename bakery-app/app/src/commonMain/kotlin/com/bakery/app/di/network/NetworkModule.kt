package com.bakery.app.di.network

import com.bakery.network.client.base.NetworkClient
import com.bakery.network.client.ktor.KtorClientImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun networkModule(): Module = module {
    single<NetworkClient> {
        KtorClientImpl(
            baseUrl = "https://bakeryanddeserts.com/",
        )
    }
}
