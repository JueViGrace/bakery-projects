package com.bakery.ui.components.containers

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bakery.ui.window.LocalWindowUtils
import com.bakery.ui.window.WindowUtils

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    landscapeLayout: @Composable () -> Unit,
    portraitLayout: @Composable () -> Unit,
) {
    val windowUtils: WindowUtils = LocalWindowUtils.current
    Surface(
        modifier = modifier,
        color = color,
    ) {
        when (windowUtils.isWideLayout()) {
            true -> {
                landscapeLayout()
            }

            false -> {
                portraitLayout()
            }
        }
    }
}
