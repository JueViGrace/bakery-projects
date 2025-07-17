package com.bakery.auth.signin.domain.model

import androidx.compose.runtime.Immutable
import com.bakery.auth.network.model.dto.SignInDto

@Immutable
data class SignInForm(
    val username: String = "",
    val password: String = "",

    val showUsernameError: Boolean = false,
    val showPasswordError: Boolean = false,
) {
    val hasErrors: Boolean = showUsernameError && showPasswordError

    fun toDto(): SignInDto = SignInDto(
        username = username,
        password = password,
    )
}
