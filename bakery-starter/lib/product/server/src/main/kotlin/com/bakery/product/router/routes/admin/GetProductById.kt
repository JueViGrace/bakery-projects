package com.bakery.product.router.routes.admin

import com.bakery.core.types.ServerResponse
import com.bakery.core.types.applicationResponse
import com.bakery.product.data.handler.ProductHandler
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.adminGetProductById(handler: ProductHandler) {
    get("/{id}") {
        val id =
            call.parameters["id"]
                ?: return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    message =
                        ServerResponse.badRequest<String?>(
                            message = "Missing id",
                        ),
                )
        val response = handler.getProductById(id)

        call.applicationResponse(
            response = response,
            onFailure = { res ->
                call.respond(
                    status =
                        HttpStatusCode(
                            value = res.status,
                            description = res.description,
                        ),
                    message = res,
                )
            },
            onSuccess = { res ->
                call.respond(
                    status =
                        HttpStatusCode(
                            value = res.status,
                            description = res.description,
                        ),
                    message = res,
                )
            },
        )
    }
}
