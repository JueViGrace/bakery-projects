package com.bakery.profile.presentation.events

sealed interface ProfileEvents {
    data object OnLogOut : ProfileEvents
}
