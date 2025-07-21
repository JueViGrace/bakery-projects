package com.bakery.home.presentation.events

import com.bakery.ui.layout.bars.NavBars

sealed interface HomeEvents {
    /* Bar events */

    data object HideBottomBar : HomeEvents
    data object OnCart : HomeEvents
    data object OnNotifications : HomeEvents
    data class OnTabSelected(val index: Int, val bar: NavBars) : HomeEvents
    data object ToggleMainBar : HomeEvents
}
