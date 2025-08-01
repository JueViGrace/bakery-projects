package com.bakery.product.router.routes.admin

import com.bakery.core.shared.types.product.CreateProductDto
import com.bakery.core.types.applicationResponse
import com.bakery.product.data.handler.ProductHandler
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.createProduct(handler: ProductHandler) {
    // todo: get path for images
    post<CreateProductDto> { body ->
//                    val multipart = call.receiveMultipart()
        val response =
            handler.createProduct(
                dto = body,
                images = emptyList(),
            )

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
