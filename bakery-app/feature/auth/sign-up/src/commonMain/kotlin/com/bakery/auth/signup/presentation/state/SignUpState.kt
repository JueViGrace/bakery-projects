package com.bakery.auth.signup.presentation.state

import com.bakery.auth.signup.domain.rules.SignUpValidation
import com.bakery.auth.types.signup.SignUpForm

data class SignUpState(
    val signUpForm: SignUpForm = SignUpForm(),
    val formValidation: SignUpValidation = SignUpValidation(),

    val submitLoading: Boolean = false,

    val submitError: String? = null,
    val submitEnabled: Boolean = formValidation.valid(),
)
