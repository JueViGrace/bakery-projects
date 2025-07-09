package com.bakery.auth.signin.presentation.state

import com.bakery.auth.signin.domain.rules.LoginValidation
import com.bakery.auth.types.signin.LogInForm

data class SignInState(
    val logInForm: LogInForm = LogInForm(),
    val formValidation: LoginValidation = LoginValidation(),

    val submitLoading: Boolean = false,

    val submitError: String? = null,
    val submitEnabled: Boolean = false,
)
