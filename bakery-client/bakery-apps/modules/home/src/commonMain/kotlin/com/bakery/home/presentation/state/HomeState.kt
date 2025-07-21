package com.bakery.home.presentation.state

import com.bakery.ui.layout.bars.NavBarState

data class HomeState(
    // Bottom nav
    val bottomBarState: NavBarState = NavBarState(),

    val innerTopBarState: NavBarState = NavBarState(),
)
