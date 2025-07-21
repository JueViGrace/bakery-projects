package com.bakery.ui.components.containers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import com.bakery.ui.components.layout.scaffold.DefaultScaffold

typealias ScaffoldType = @Composable (
    contentWindowInsets: WindowInsets,
    snackbarHost: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) -> Unit

@Composable
expect fun ScaffoldContainer(
    scaffold: ScaffoldType = { contentWindowInsets, snackbarHost, content ->
        DefaultScaffold(
            contentWindowInsets = contentWindowInsets,
            snackbarHost = snackbarHost,
            content = content,
        )
    },
    content: @Composable () -> Unit,
)
