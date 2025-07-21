package com.bakery.ui.components.layout.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DefaultScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().then(modifier),
        topBar = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                topBar()
            }
        },
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        snackbarHost = snackbarHost,
        contentWindowInsets = contentWindowInsets,
        content = content,
    )
}
