package com.bakery.api.products.routes

import com.bakery.api.products.service.ProductService
import com.bakery.common.applicationResponse
import com.bakery.models.product.CreateProductDto
import com.bakery.models.response.DefaultHttpResponse
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.productRoutes() {
    route("/products") {
        val productService: ProductService by inject<ProductService>()

        get {
            val products = productService.getAllProducts()

            call.applicationResponse(products)
        }

        get("{id}") {
            val idParam = call.parameters["id"]
                ?: return@get call.respond(DefaultHttpResponse.badRequest("Parameter id must be provided"))

            if (idParam.map { it.isDigit() }.contains(false)) {
                return@get call.respond(
                    DefaultHttpResponse.badRequest("Parameter id must be a valid number")
                )
            }

            val product = productService.getOneProductById(idParam.toInt())

            call.applicationResponse(product)
        }

        authenticate("role-auth") {
            post {
                val body = call.receive<CreateProductDto>()

                val product = productService.createProduct(body)

                call.applicationResponse(product)
            }

            put {
                val idParam = call.parameters["id"]
                    ?: return@put call.respond(DefaultHttpResponse.badRequest("Parameter id must be provided"))

                if (idParam.map { it.isDigit() }.contains(false)) {
                    return@put call.respond(
                        DefaultHttpResponse.badRequest("Parameter id must be a valid number")
                    )
                }

                val body = call.receive<CreateProductDto>()

                val product = productService.updateProduct(idParam.toInt(), body)

                call.applicationResponse(product)
            }

            delete {
                val idParam = call.parameters["id"]
                    ?: return@delete call.respond(
                        DefaultHttpResponse.badRequest("Parameter id must be provided")
                    )

                if (idParam.map { it.isDigit() }.contains(false)) {
                    return@delete call.respond(
                        DefaultHttpResponse.badRequest("Parameter id must be a valid number")
                    )
                }

                val deletedProduct = productService.deleteProduct(id = idParam.toInt())

                call.applicationResponse(deletedProduct)
            }
        }
    }
}
