package com.bakery.ui.layout.bars

import com.bakery.ui.navigation.tab.Tab

data class NavBarState(
    val tabs: List<Tab> = emptyList(),
    val selectedTabIndex: Int = 0,
    val selectedTab: Tab? = if (tabs.isNotEmpty()) {
        tabs[0]
    } else {
        null
    },
    val showBar: Boolean = true,
)
