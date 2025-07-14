package com.bakery.ui.components.containers

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bakery.ui.components.layout.scaffold.DefaultScaffold

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    scaffold: ScaffoldType = { snackbarHost, content ->
        DefaultScaffold(
            snackbarHost = snackbarHost,
            content = content,
        )
    },
    color: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    ScaffoldContainer(
        scaffold = scaffold,
        content = {
            Surface(
                modifier = modifier,
                color = color,
            ) {
                content()
            }
        },
    )
}
