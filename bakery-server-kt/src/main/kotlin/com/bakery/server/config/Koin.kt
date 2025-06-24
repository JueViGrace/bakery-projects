package com.bakery.server.config

import com.bakery.api.auth.di.authModule
import com.bakery.api.cart.di.cartModule
import com.bakery.api.orders.di.orderModule
import com.bakery.api.products.di.productModule
import com.bakery.api.users.di.userModule
import com.bakery.server.di.coroutinesModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(if (developmentMode) Level.DEBUG else Level.INFO)
        modules(
            coroutinesModule,
            userModule,
            authModule,
            productModule,
            cartModule,
            orderModule
        )
    }
}
