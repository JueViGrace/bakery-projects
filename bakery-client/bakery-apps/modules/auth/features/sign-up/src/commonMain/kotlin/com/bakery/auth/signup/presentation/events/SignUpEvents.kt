package com.bakery.auth.signup.presentation.events

sealed interface SignUpEvents {
    data class OnFirstNameChanged(val value: String) : SignUpEvents
    data class OnLastNameChanged(val value: String) : SignUpEvents
    data class OnUsernameChanged(val value: String) : SignUpEvents
    data class OnEmailChanged(val value: String) : SignUpEvents
    data class OnPhoneChanged(val value: String) : SignUpEvents
    data class OnAliasChanged(val value: String) : SignUpEvents
    data class OnPasswordChanged(val value: String) : SignUpEvents
    data class OnConfirmPasswordChanged(val value: String) : SignUpEvents
    data object OnPrivacyPolicy : SignUpEvents
    data object TogglePrivacyPolicy : SignUpEvents
    data object OnTerms : SignUpEvents
    data object ToggleTerms : SignUpEvents
    data object OnSubmit : SignUpEvents
}
