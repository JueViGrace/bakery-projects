package com.bakery.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("user_id")
    val userId: Int,
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
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
)
