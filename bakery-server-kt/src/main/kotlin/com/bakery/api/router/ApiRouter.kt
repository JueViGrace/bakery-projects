package com.bakery.api.router

import com.bakery.api.auth.routes.authRoutes
import com.bakery.api.cart.routes.cartRoutes
import com.bakery.api.orders.routes.orderRoutes
import com.bakery.api.products.routes.productRoutes
import com.bakery.api.users.routes.userRoutes
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.apiRoutes() {
    route("/api") {
        authRoutes()
        userRoutes()
        cartRoutes()
        orderRoutes()
        productRoutes()
    }
}
