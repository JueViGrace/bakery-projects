package com.bakery.api.auth.validation

import com.bakery.common.Constants.validEmail
import com.bakery.models.auth.RegisterDto
import com.bakery.common.validDate
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateRegisterDto() {
    validate<RegisterDto> { dto ->
        when {
            dto.name.isEmpty() -> ValidationResult.Invalid("Name must not be empty")
            dto.lastname.isEmpty() -> ValidationResult.Invalid("Last name must not be empty")
            !validEmail(dto.email) -> ValidationResult.Invalid("Email must be a valid email")
            !dto.birthDate.validDate() -> ValidationResult.Invalid("Birth date is invalid")
            dto.phone.isEmpty() -> ValidationResult.Invalid("Phone must not be empty")
            dto.password.isEmpty() -> ValidationResult.Invalid("Password must not be empty")
            else -> ValidationResult.Valid
        }
    }
}
