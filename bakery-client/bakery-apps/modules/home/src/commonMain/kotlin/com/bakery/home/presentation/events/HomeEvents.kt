package com.bakery.home.presentation.events

import com.bakery.ui.layout.bars.NavBars

sealed interface HomeEvents {
    /* Bar events */

    data class OnTabSelected(val index: Int, val bar: NavBars) : HomeEvents
    data object OnAccount : HomeEvents
    data object OnNotifications : HomeEvents
    data object ToggleSearch : HomeEvents
    data object ToggleMore : HomeEvents
}
