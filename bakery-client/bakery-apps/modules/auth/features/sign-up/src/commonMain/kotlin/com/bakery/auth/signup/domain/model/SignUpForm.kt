package com.bakery.auth.signup.domain.model

import com.bakery.auth.network.model.dto.SignUpDto

data class SignUpForm(
    val firstName: String = "",
    val lastName: String = "",
    val alias: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val termsAndConditions: Boolean = false,
    val privacyPolicy: Boolean = false,

    val showFirstNameError: Boolean = false,
    val showLastNameError: Boolean = false,
    val showAliasError: Boolean = false,
    val showPhoneNumberError: Boolean = false,
    val showEmailError: Boolean = false,
    val showUsernameError: Boolean = false,
    val showPasswordError: Boolean = false,
    val showConfirmPasswordError: Boolean = false,
    val showTermsAndConditionsError: Boolean = false,
    val showPrivacyPolicyError: Boolean = false,
) {
    val hasErrors: Boolean = showFirstNameError ||
        showLastNameError ||
        showAliasError ||
        showPhoneNumberError ||
        showEmailError ||
        showUsernameError ||
        showPasswordError ||
        showConfirmPasswordError ||
        showTermsAndConditionsError ||
        showPrivacyPolicyError

    fun toDto(): SignUpDto = SignUpDto(
        firstName = firstName,
        lastName = lastName,
        username = username,
        alias = alias,
        email = email,
        password = password,
        phoneNumber = phoneNumber,
    )
}
