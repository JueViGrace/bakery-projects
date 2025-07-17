package com.bakery.auth.signin.presentation.events

import com.bakery.ui.model.SocialProvider
import com.bakery.ui.navigation.models.ForgotAction

sealed interface SignInEvents {
    /* Input */
    data class OnUsernameChanged(val email: String) : SignInEvents
    data class OnPasswordChanged(val password: String) : SignInEvents

    /* Navigation */
    data class OnForgot(val action: ForgotAction) : SignInEvents
    data object OnSignUp : SignInEvents

    /* Actions */
    data class OnSocialLogin(val provider: SocialProvider) : SignInEvents
    data object OnSubmit : SignInEvents
}
