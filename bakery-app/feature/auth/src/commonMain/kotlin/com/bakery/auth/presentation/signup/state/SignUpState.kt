package com.bakery.auth.presentation.signup.state

import com.bakery.auth.domain.model.SignUpForm
import com.bakery.auth.domain.rules.SignUpValidation

data class SignUpState(
    val signUpForm: SignUpForm = SignUpForm(),
    val formValidation: SignUpValidation = SignUpValidation(),

    val submitLoading: Boolean = false,

    val submitError: String? = null,
    val submitEnabled: Boolean = formValidation.valid(),
)
