package com.bakery.api.auth.validation

import com.bakery.common.Constants.validEmail
import com.bakery.models.auth.LoginDto
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateLoginDto() {
    validate<LoginDto> { dto ->
        when {
            !validEmail(dto.email) -> ValidationResult.Invalid("Email must be a valid email")
            dto.password.isEmpty() -> ValidationResult.Invalid("Password must not be empty")
            else -> ValidationResult.Valid
        }
    }
}
