package com.bakery.ui.layout.bars

sealed interface NavBars {
    data object TopBar : NavBars
    data object BottomBar : NavBars
    data object SideBar : NavBars
}