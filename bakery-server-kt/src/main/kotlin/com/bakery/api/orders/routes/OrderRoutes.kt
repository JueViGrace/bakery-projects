package com.bakery.api.orders.routes

import com.bakery.api.orders.service.OrderService
import com.bakery.common.applicationResponse
import com.bakery.common.extractUserIdFromPrincipal
import com.bakery.models.order.UpdateOrderStatus
import com.bakery.models.response.AppResponse.SuccessResponse
import com.bakery.models.response.DefaultHttpResponse
import io.ktor.server.application.call
import io.ktor.server.auth.AuthenticationStrategy.Required
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.orderRoutes() {
    route("/orders") {
        authenticate("user-auth", strategy = Required) {
            val orderService: OrderService by inject()

            route("/user") {
                get {
                    val userId = extractUserIdFromPrincipal()
                        ?: return@get call.respond(DefaultHttpResponse.badRequest(message = "Invalid token"))

                    val orders = orderService.getUserOrders(userId)

                    call.applicationResponse(orders)
                }

                get("/{id}") {
                    val idParam = call.parameters["id"]
                        ?: return@get call.respond(DefaultHttpResponse.badRequest("Parameter id must be provided"))

                    if (idParam.map { it.isDigit() }.contains(false)) {
                        return@get call.respond(
                            DefaultHttpResponse.badRequest("Parameter id must be a valid number")
                        )
                    }

                    val userId = extractUserIdFromPrincipal()
                        ?: return@get call.respond(DefaultHttpResponse.badRequest(message = "Invalid token"))

                    val order = orderService.getOneOrderById(id = idParam.toInt())

                    if (order is SuccessResponse && order.body.userId != userId) {
                        return@get call.respond(DefaultHttpResponse.forbidden())
                    }

                    call.applicationResponse(order)
                }
            }

            authenticate("role-auth", strategy = Required) {
                get {
                    val orders = orderService.getAllOrders()

                    call.applicationResponse(orders)
                }

                put {
                    val body = call.receive<UpdateOrderStatus>()

                    val message = orderService.updateOrderStatus(body)

                    call.applicationResponse(message)
                }

                delete("/{id}") {
                    val idParam = call.parameters["id"]
                        ?: return@delete call.respond(DefaultHttpResponse.badRequest("Parameter id must be provided"))

                    if (idParam.map { it.isDigit() }.contains(false)) {
                        return@delete call.respond(
                            DefaultHttpResponse.badRequest("Parameter id must be a valid number")
                        )
                    }

                    val response = orderService.softDeleteOrder(idParam.toInt())

                    call.applicationResponse(response)
                }
            }
        }
    }
}
