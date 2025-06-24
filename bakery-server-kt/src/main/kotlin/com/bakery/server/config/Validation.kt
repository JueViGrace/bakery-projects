package com.bakery.server.config

import com.bakery.api.auth.validation.validateLoginDto
import com.bakery.api.auth.validation.validateRegisterDto
import com.bakery.api.users.validation.validateUserDto
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        validateUserDto()
        validateRegisterDto()
        validateLoginDto()
    }
}
