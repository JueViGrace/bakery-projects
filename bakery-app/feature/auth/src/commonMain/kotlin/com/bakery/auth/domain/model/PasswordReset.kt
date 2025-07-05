package com.bakery.auth.domain.model

import com.bakery.auth.network.model.dto.PasswordResetDto

data class PasswordReset(
    val username: String,
    val password: String,
    val newPassword: String,
) {
    fun toDto(): PasswordResetDto {
        return PasswordResetDto(
            username = username,
            password = password,
            newPassword = newPassword
        )
    }
}
