package com.bakery.auth.types.forgot

import com.bakery.auth.network.model.dto.PasswordResetDto

data class PasswordReset(val username: String, val password: String, val newPassword: String) {
    fun toDto(): PasswordResetDto = PasswordResetDto(
        username = username,
        password = password,
        newPassword = newPassword,
    )
}
