package com.bakery.auth.signup.presentation.state

import com.bakery.auth.signup.domain.model.SignUpForm
import com.bakery.auth.signup.domain.rules.SignUpValidation

data class SignUpState(
    val signUpForm: SignUpForm = SignUpForm(),
    val formValidation: SignUpValidation = SignUpValidation(),

    val pageIndex: Int = 0,
    val nextEnabled: Boolean = false,

    val submitLoading: Boolean = false,
    val submitError: String? = null,
    val submitEnabled: Boolean = formValidation.valid(),
    val submitSuccess: Boolean = false,
)
