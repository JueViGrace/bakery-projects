package com.bakery.types.validation

object EmailValidator {
    // RFC 5322 compliant (simplified)
    private val rfc5322Regex = Regex(
        """^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"""
    )

    // Stricter pattern for most use cases
    private val strictEmailRegex = Regex(
        """^[a-zA-Z0-9][a-zA-Z0-9._%+-]{0,63}@(?:[a-zA-Z0-9-]{1,63}\.)+[a-zA-Z]{2,}$"""
    )

    fun validate(email: String, strict: Boolean = true): EmailValidationResult {
        val trimmed = email.trim()

        if (trimmed.isEmpty()) {
            return EmailValidationResult.Invalid(EmailValidationError.EMPTY)
        }

        if (trimmed.length > 254) {
            return EmailValidationResult.Invalid(EmailValidationError.TOO_LONG)
        }

        val regex = if (strict) strictEmailRegex else rfc5322Regex

        return if (regex.matches(trimmed)) {
            EmailValidationResult.Valid
        } else {
            EmailValidationResult.Invalid(EmailValidationError.INVALID_FORMAT)
        }
    }

    fun isValid(email: String): Boolean {
        return validate(email) is EmailValidationResult.Valid
    }
}

sealed interface EmailValidationResult {
    data object Valid : EmailValidationResult
    data class Invalid(val reason: EmailValidationError) : EmailValidationResult
}

enum class EmailValidationError{
    EMPTY,
    TOO_LONG,
    INVALID_FORMAT,
}

fun String.isValidEmail(): Boolean {
    return EmailValidator.isValid(this)
}

fun String.validateEmail(): EmailValidationResult {
    return EmailValidator.validate(this)
}
