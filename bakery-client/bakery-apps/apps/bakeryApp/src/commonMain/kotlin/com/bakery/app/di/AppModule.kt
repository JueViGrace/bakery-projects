package com.bakery.app.di

import com.bakery.app.data.AppRepository
import com.bakery.app.data.DefaultAppRepository
import com.bakery.app.di.database.databaseModule
import com.bakery.app.di.network.networkModule
import com.bakery.app.presentation.viewmodel.AppViewModel
import com.bakery.auth.di.authModule
import com.bakery.home.di.homeModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/*
* Application dependency injection module.
* */
fun appModule(): Module = module {
    // Core modules
    includes(databaseModule(), networkModule())

    // Feature modules
    includes(authModule(), homeModule())

    singleOf(::DefaultAppRepository) bind AppRepository::class

    viewModelOf(::AppViewModel)
}
