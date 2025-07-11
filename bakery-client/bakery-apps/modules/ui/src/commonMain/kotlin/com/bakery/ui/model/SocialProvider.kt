package com.bakery.ui.model

sealed interface SocialProvider {
    data object Google : SocialProvider
    data object Facebook : SocialProvider
    data object Twitter : SocialProvider
}
