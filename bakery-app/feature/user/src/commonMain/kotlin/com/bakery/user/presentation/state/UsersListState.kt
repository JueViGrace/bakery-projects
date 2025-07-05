package com.bakery.user.presentation.state

import com.bakery.types.user.User

data class UsersListState(
    val users: List<User> = emptyList(),
)
