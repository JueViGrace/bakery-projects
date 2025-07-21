package com.bakery.profile.presentation.state

import com.bakery.types.user.User

data class ProfileState(
    val user: User? = null,
)
