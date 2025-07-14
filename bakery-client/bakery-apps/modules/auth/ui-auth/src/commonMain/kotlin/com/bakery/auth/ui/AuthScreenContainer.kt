package com.bakery.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.bakery.ui.components.containers.ScaffoldType
import com.bakery.ui.components.containers.ScreenContainer
import com.bakery.ui.components.layout.scaffold.DefaultScaffold

@Composable
fun AuthScreenContainer(
    scaffold: ScaffoldType = { snackbarHost, content ->
        DefaultScaffold(
            snackbarHost = snackbarHost,
            content = content,
        )
    },
    content: @Composable () -> Unit
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
            )
            .fillMaxSize(),
        scaffold = scaffold,
        color = Color.Transparent,
        content = content,
    )
}
