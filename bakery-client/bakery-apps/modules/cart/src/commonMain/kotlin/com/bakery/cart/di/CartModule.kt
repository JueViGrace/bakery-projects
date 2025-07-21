package com.bakery.cart.di

import com.bakery.cart.data.CartRepository
import com.bakery.cart.data.DefaultCartRepository
import com.bakery.cart.presentation.viewmodel.CartViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun cartModule(): Module = module {
    singleOf(::DefaultCartRepository) bind CartRepository::class

    viewModelOf(::CartViewModel)
}
