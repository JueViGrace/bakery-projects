package com.bakery.api.products.di

import com.bakery.api.products.service.ProductService
import com.bakery.api.products.repository.ProductRepository
import com.bakery.api.products.repository.ProductRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val productModule = module {
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class

    singleOf(::ProductService)
}
