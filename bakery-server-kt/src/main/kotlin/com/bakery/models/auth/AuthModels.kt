package com.bakery.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
)

@Serializable
data class RegisterDto(
    @SerialName("name")
    val name: String,
    @SerialName("lastname")
    val lastname: String,
    @SerialName("email")
    val email: String,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("password")
    val password: String,
)

@Serializable
data class AuthResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
)

@Serializable
data class RefreshTokenRequest(
    @SerialName("refresh_token")
    val refreshToken: String,
)

data class Token(
    val token: String,
    val email: String
)

data class TokenPayload(
    val email: String,
    val role: String
)
