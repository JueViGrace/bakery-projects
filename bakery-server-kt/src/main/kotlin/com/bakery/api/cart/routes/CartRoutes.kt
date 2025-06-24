package com.bakery.api.cart.routes

import com.bakery.api.cart.service.CartService
import com.bakery.common.applicationResponse
import com.bakery.models.cart.AddToCartDto
import com.bakery.common.extractUserIdFromPrincipal
import com.bakery.models.response.DefaultHttpResponse
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.cartRoutes() {
    route("/cart") {
        authenticate("user-auth") {
            val cartService: CartService by inject()

            get {
                val userId = extractUserIdFromPrincipal()
                    ?: return@get call.respond(DefaultHttpResponse.badRequest())

                val cart = cartService.getCart(userId)

                call.applicationResponse(cart)
            }

            post("/checkout") {
                val userId = extractUserIdFromPrincipal()
                    ?: return@post call.respond(DefaultHttpResponse.badRequest(message = "Invalid token"))

                val savedOrder = cartService.checkout(userId)

                call.applicationResponse(savedOrder)
            }

            post("/add") {
                val body = call.receive<AddToCartDto>()

                val savedCart = cartService.addItem(body)

                call.applicationResponse(savedCart)
            }

            delete("/remove") {
                val body = call.receive<AddToCartDto>()

                val updatedCart = cartService.removeItem(body)

                call.applicationResponse(updatedCart)
            }
        }
    }
}
