package com.bakery.auth.network.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogInDto(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)
