package com.bakery.server.router

import com.bakery.api.router.apiRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.appRoutes() {
    routing {
        apiRoutes()
        // webRoutes()
    }
}
