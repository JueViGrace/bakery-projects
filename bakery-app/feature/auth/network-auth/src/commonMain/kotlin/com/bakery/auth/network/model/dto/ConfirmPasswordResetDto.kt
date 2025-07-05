package com.bakery.auth.network.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmPasswordResetDto(
    @SerialName("code")
    val code: String,
)
