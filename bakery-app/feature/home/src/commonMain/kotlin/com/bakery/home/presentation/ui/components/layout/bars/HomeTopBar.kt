package com.bakery.home.presentation.ui.components.layout.bars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.bakery.ui.components.layout.bars.top.TopNavBar
import com.bakery.ui.layout.bars.NavBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    state: NavBarState = NavBarState(),
    navigationIcon: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
) {
    TopNavBar(
        state = state,
        navigationIcon = navigationIcon,
        title = title,
        actions = actions,
        onTabSelected = onTabSelected
    )
}
