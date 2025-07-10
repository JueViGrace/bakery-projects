package com.bakery.app.di.network

import com.bakery.network.client.base.NetworkClient
import com.bakery.network.client.ktor.KtorClientImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun networkModule(): Module = module {
    singleOf(::KtorClientImpl) bind NetworkClient::class
}
