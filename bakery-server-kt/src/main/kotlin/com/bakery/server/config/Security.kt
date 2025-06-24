package com.bakery.server.config

import com.bakery.api.auth.service.JwtService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val jwtService by inject<JwtService>(parameters = {
        parametersOf(
            environment.config.property("ktor.jwt.secret").getString(),
            environment.config.property("ktor.jwt.issuer").getString(),
            environment.config.property("ktor.jwt.audience").getString(),
            environment.config.property("ktor.jwt.realm").getString(),
        )
    })

    install(Authentication) {
        jwt("user-auth") {
            realm = jwtService.realm

            verifier(jwtService.jwtVerifier)

            validate { credential ->
                jwtService.userValidator(credential)
            }
        }

        jwt("role-auth") {
            realm = jwtService.realm

            verifier(jwtService.jwtVerifier)

            validate { credential ->
                jwtService.roleValidator(credential)
            }
        }
    }
}
