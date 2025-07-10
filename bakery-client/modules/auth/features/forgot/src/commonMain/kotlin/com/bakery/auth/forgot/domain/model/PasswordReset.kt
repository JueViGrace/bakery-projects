package com.bakery.types.auth.forgot

import com.bakery.auth.network.model.dto.PasswordResetDto

data class PasswordReset(val username: String, val password: String, val newPassword: String) {
    fun toDto(): PasswordResetDto = PasswordResetDto(
        username = username,
        password = password,
        newPassword = newPassword,
    )
}
