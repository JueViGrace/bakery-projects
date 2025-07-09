package com.bakery.ui.components.layout.bars.bottom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.bakery.ui.layout.bars.NavBarState

@Composable
fun BottomNavBar(
    state: NavBarState = remember { NavBarState() },
    floatingActionButton: (@Composable () -> Unit)? = null,
    onTabSelected: (index: Int) -> Unit,
) {
    AnimatedVisibility(
        visible = state.showBar,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        BottomBarComponent(
            tabs = state.tabs,
            selected = { tab -> tab == state.selectedTab },
            onTabSelected = onTabSelected,
            floatingActionButton = floatingActionButton,
        )
    }
}
