package com.bakery.ui.components.layout.bars.top

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.bakery.ui.window.LocalWindowUtils
import com.bakery.ui.window.ScreenSize
import com.bakery.ui.window.WindowUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    expandedHeight: Dp = calculateTopBarHeight(),
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        actions = actions,
        expandedHeight = expandedHeight,
        navigationIcon = navigationIcon,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun calculateTopBarHeight(): Dp {
    val windowUtils: WindowUtils = LocalWindowUtils.current
    return when (windowUtils.getScreenSize()) {
        ScreenSize.Compact -> TopAppBarDefaults.TopAppBarExpandedHeight
        ScreenSize.Medium -> TopAppBarDefaults.MediumAppBarExpandedHeight
        ScreenSize.Large -> TopAppBarDefaults.LargeAppBarExpandedHeight
    }
}
