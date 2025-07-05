package com.bakery.auth.presentation.signin.state

import com.bakery.auth.domain.model.LogInForm
import com.bakery.auth.domain.rules.LoginValidation

data class SignInState(
    val logInForm: LogInForm = LogInForm(),
    val formValidation: LoginValidation = LoginValidation(),

    val submitLoading: Boolean = false,

    val submitError: String? = null,
    val submitEnabled: Boolean = false,
)
