package com.bakery.server.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val coroutinesModule = module {
    single<CoroutineContext> {
        Dispatchers.IO
    }

    singleOf(::CoroutineScope) bind CoroutineScope::class
}
