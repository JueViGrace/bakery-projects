package com.bakery.auth.signin.presentation.state

import com.bakery.auth.signin.domain.model.SignInForm
import com.bakery.auth.signin.domain.rules.SignInValidation

data class SignInState(
    val signInForm: SignInForm = SignInForm(),
    val formValidation: SignInValidation = SignInValidation(),

    val submitLoading: Boolean = false,

    val submitError: String? = null,
    val submitEnabled: Boolean = false,
)
