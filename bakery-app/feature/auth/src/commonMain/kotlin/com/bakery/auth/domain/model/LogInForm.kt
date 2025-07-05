package com.bakery.auth.domain.model

import com.bakery.auth.network.model.dto.LogInDto

data class LogInForm(
    val username: String = "",
    val password: String = "",
    val shouldShowUsernameError: Boolean = false,
    val shouldShowPasswordError: Boolean = false,
) {
    fun toDto(): LogInDto {
        return LogInDto(
            username = username,
            password = password
        )
    }
}
