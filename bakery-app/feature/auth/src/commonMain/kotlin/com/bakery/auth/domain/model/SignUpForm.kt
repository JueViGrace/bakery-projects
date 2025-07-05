package com.bakery.auth.domain.model

import com.bakery.auth.network.model.dto.SignUpDto

data class SignUpForm(
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val alias: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val termsAndConditions: Boolean = false,
    val privacyPolicy: Boolean = false,
) {
    fun toDto(): SignUpDto {
        return SignUpDto(
            firstName = firstName,
            lastName = lastName,
            username = username,
            alias = alias,
            email = email,
            password = password,
            phoneNumber = phoneNumber,
        )
    }
}
