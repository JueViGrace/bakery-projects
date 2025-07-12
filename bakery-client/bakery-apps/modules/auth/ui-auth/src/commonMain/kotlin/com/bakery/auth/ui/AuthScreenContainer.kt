package com.bakery.auth.ui

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.bakery.ui.components.containers.ScreenContainer

@Composable
fun AuthScreenContainer(
    landscapeLayout: @Composable () -> Unit,
    portraitLayout: @Composable () -> Unit,
) {
    ScreenContainer(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    ),
                ),
            ),
        color = Color.Transparent,
        landscapeLayout = landscapeLayout,
        portraitLayout = portraitLayout
    )
}
