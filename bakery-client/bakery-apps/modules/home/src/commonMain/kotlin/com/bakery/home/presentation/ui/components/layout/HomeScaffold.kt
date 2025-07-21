package com.bakery.home.presentation.ui.components.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import com.bakery.home.presentation.events.HomeEvents
import com.bakery.home.presentation.state.HomeState
import com.bakery.ui.components.layout.bars.bottom.BottomNavBar
import com.bakery.ui.components.layout.scaffold.AdaptativeScaffold
import com.bakery.ui.layout.bars.NavBars
import com.bakery.ui.window.LocalWindowUtils
import com.bakery.ui.window.WindowUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(
    state: HomeState,
    onEvent: (HomeEvents) -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val windowsUtils: WindowUtils = LocalWindowUtils.current
    val isLandscape: Boolean = windowsUtils.isLandscape()
    AdaptativeScaffold(
        hasDrawer = isLandscape,
        bottomBar = {
            if (!isLandscape) {
                BottomNavBar(
                    state = state.bottomBarState,
                    onTabSelected = { index ->
                        onEvent(HomeEvents.OnTabSelected(index, NavBars.BottomBar))
                    },
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        contentWindowInsets = contentWindowInsets,
        snackbarHost = snackbarHost,
        content = content,
    )
}
