package com.bakery.ui.components.layout.scaffold

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bakery.ui.components.menus.DrawerComponent

@Composable
fun AdaptativeScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    hasDrawer: Boolean = false,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    drawerContent: @Composable ColumnScope.() -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val scaffold: @Composable () -> Unit = {
        DefaultScaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            contentWindowInsets = contentWindowInsets,
            snackbarHost = snackbarHost,
            content = content,
        )
    }

    if (hasDrawer) {
        DrawerComponent(
            drawerState = drawerState,
            drawerContent = drawerContent,
            content = {
                scaffold()
            }
        )
    } else {
        scaffold()
    }
}
