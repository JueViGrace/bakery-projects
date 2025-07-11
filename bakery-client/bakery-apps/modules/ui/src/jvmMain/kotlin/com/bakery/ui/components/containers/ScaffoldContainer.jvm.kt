package com.bakery.ui.components.containers

import androidx.compose.runtime.Composable
import com.bakery.ui.components.layout.scaffold.ScaffoldContent

@Composable
actual fun ScaffoldContainer(scaffold: ScaffoldType, content: @Composable (() -> Unit)) {
    ScaffoldContent(
        scaffold = scaffold,
        content = content,
    )
}
