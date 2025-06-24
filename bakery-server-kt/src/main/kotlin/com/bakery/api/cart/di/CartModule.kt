package com.bakery.api.cart.di

import com.bakery.api.cart.service.CartService
import com.bakery.api.cart.repository.CartRepository
import com.bakery.api.cart.repository.CartRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cartModule = module {
    singleOf(::CartRepositoryImpl) bind CartRepository::class

    singleOf(::CartService)
}
