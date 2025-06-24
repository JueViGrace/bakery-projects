package com.bakery.api.users.validation

import com.bakery.common.Constants.validEmail
import com.bakery.models.user.UserDto
import com.bakery.common.validDate
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateUserDto() {
    validate<UserDto> { dto ->
        when {
            dto.userId <= 0 -> ValidationResult.Invalid("Id must be a valid id")
            dto.name.isEmpty() -> ValidationResult.Invalid("Name must not be empty")
            dto.lastname.isEmpty() -> ValidationResult.Invalid("Last name must not be empty")
            !validEmail(dto.email) -> ValidationResult.Invalid("Email must be a valid email")
            !dto.birthDate.validDate() -> ValidationResult.Invalid("Birth date is invalid")
            dto.phone.isEmpty() -> ValidationResult.Invalid("Phone must not be empty")
            else -> ValidationResult.Valid
        }
    }
}
