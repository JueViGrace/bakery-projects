package com.bakery.ui.navigation.models

import kotlinx.serialization.Serializable

@Serializable
sealed interface ForgotAction {
    @Serializable
    data object OnForgotPassword : ForgotAction

    @Serializable
    data object OnForgotUsername : ForgotAction
}
