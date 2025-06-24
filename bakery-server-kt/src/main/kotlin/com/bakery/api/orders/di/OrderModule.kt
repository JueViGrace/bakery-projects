package com.bakery.api.orders.di

import com.bakery.api.orders.service.OrderService
import com.bakery.api.orders.repository.OrderRepository
import com.bakery.api.orders.repository.OrderRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val orderModule = module {
    singleOf(::OrderRepositoryImpl) bind OrderRepository::class

    singleOf(::OrderService)
}
