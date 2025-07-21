package com.bakery.home.di

import com.bakery.cart.di.cartModule
import com.bakery.home.data.DefaultHomeRepository
import com.bakery.home.data.HomeRepository
import com.bakery.home.presentation.viewmodel.HomeViewModel
import com.bakery.notification.di.notificationModule
import com.bakery.order.di.orderModule
import com.bakery.product.di.productModule
import com.bakery.profile.di.profileModule
import com.bakery.user.di.userModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun homeModule(): Module = module {
    includes(
        cartModule(),
        productModule(),
        profileModule(),
        notificationModule(),
        orderModule(),
        userModule(),
    )

    singleOf(::DefaultHomeRepository) bind HomeRepository::class

    viewModelOf(::HomeViewModel)
}
