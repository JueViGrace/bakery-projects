package com.bakery.auth.network.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDto(
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("email")
    val email: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)
