package com.bakery.auth.signin.domain.model

import androidx.compose.runtime.Immutable
import com.bakery.auth.network.model.dto.LogInDto

@Immutable
data class SignInForm(
    val username: String = "",
    val password: String = "",

    val showUsernameError: Boolean = false,
    val showPasswordError: Boolean = false,
) {
    val hasErrors: Boolean = showUsernameError || showPasswordError

    fun toDto(): LogInDto = LogInDto(
        username = username,
        password = password,
    )
}
