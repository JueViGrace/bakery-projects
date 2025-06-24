package com.bakery.api.auth.routes

import com.bakery.api.auth.service.AuthService
import com.bakery.common.applicationResponse
import com.bakery.models.auth.LoginDto
import com.bakery.models.auth.RefreshTokenRequest
import com.bakery.models.auth.RegisterDto
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    route("/auth") {
        val authService: AuthService by inject<AuthService>()
        post("/register") {
            val body = call.receive<RegisterDto>()

            val savedUser = authService.register(body)
            call.applicationResponse(savedUser)
        }

        post("/login") {
            val body = call.receive<LoginDto>()

            val newUser = authService.login(body)

            call.applicationResponse(newUser)
        }

        post("/refresh") {
            val body = call.receive<RefreshTokenRequest>()

            val newAccessToken = authService.refreshToken(token = body.refreshToken)

            call.applicationResponse(newAccessToken)
        }
    }
}
