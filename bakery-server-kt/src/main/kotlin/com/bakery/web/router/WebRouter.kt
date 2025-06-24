package com.bakery.web.router

import com.bakery.models.product.ProductDto
import com.bakery.web.client.ApiOperation.Failure
import com.bakery.web.client.ApiOperation.Success
import com.bakery.web.client.ApiResponse
import com.bakery.web.client.KtorClient
import com.bakery.web.view.components.products
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.thymeleaf.respondTemplate
import kotlinx.html.body
import kotlinx.html.h1

fun Route.webRoutes() {
    get("/") {
        call.respondTemplate("index.html")
    }

    get("/contact") {
    }

    get("/shop") {
    }

    get("/login") {
    }

    get("/signup") {
    }

    route("/components") {
        components()
    }
}

fun Route.components() {
    get("/container") {
        call.respondTemplate("container.html")
    }

    get("/footer") {
        call.respondTemplate("footer.html")
    }

    get("/header") {
        call.respondTemplate("header.html")
    }

    get("/main") {
        call.respondTemplate("main.html")
    }

    route("/products") {
        get {
            val products = KtorClient.safeApiCall {
                KtorClient.client()
                    .get(urlString = "/api/products")
                    .body<ApiResponse<List<ProductDto>>>()
            }

            when (products) {
                is Failure -> call.respondHtml {
                    body {
                        h1 {
                            +"${products.message} ${products.description}"
                        }
                    }
                }

                is Success -> {
                    call.respondHtml {
                        body {
                            if (statusCodes.contains(products.data.status)) {
                                products.data.body?.let { it1 -> products(it1) }
                            } else {
                                h1 {
                                    +"${products.data.message} ${products.data.description}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


val statusCodes = listOf(200, 201, 202, 203, 204)
