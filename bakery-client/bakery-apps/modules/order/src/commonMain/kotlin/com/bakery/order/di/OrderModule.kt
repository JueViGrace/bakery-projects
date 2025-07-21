package com.bakery.order.di

import com.bakery.order.data.repository.DefaultOrderRepository
import com.bakery.order.data.repository.OrderRepository
import com.bakery.order.presentation.detail.viewmodel.OrderDetailsViewModel
import com.bakery.order.presentation.list.viewmodel.OrderListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun orderModule(): Module = module {
    singleOf(::DefaultOrderRepository) bind OrderRepository::class

    viewModelOf(::OrderListViewModel)

    viewModelOf(::OrderDetailsViewModel)
}
