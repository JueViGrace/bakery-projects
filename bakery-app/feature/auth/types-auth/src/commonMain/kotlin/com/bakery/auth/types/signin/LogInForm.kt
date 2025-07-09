package com.bakery.auth.types.signin

import com.bakery.auth.network.model.dto.LogInDto

data class LogInForm(
    val username: String = "",
    val password: String = "",
    val shouldShowUsernameError: Boolean = false,
    val shouldShowPasswordError: Boolean = false,
) {
    fun toDto(): LogInDto = LogInDto(
        username = username,
        password = password,
    )
}
