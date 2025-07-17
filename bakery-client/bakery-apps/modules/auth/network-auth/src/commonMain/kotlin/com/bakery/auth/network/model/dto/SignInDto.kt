package com.bakery.auth.network.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInDto(
    @SerialName("email")
    val username: String,
    @SerialName("password")
    val password: String,
)
