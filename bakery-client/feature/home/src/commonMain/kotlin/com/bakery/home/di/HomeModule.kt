package com.bakery.home.di

import com.bakery.home.data.DefaultHomeRepository
import com.bakery.home.data.HomeRepository
import com.bakery.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun homeModule(): Module = module {
    singleOf(::DefaultHomeRepository) bind HomeRepository::class

    viewModelOf(::HomeViewModel)
}
