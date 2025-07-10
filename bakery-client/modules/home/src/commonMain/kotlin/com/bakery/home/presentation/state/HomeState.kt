package com.bakery.home.presentation.state

import com.bakery.types.user.User
import com.bakery.ui.layout.bars.NavBarState

data class HomeState(
    val user: User? = null,

    // Bottom nav
    val bottomBarState: NavBarState = NavBarState(),

    val swipeEnable: Boolean = true,

    val showAccount: Boolean = false,
    val showNotifications: Boolean = false,
    val showMoreCategories: Boolean = false,
    val showSearch: Boolean = false,
)
