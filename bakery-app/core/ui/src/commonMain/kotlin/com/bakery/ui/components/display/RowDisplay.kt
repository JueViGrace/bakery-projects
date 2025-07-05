package com.bakery.ui.components.display

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

@Composable
fun RowDisplay(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.padding(contentPadding),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent?.let { it() }
        content()
        trailingContent?.let { it() }
    }
}

@Composable
fun RowDisplay(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enable: Boolean = true,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = enable,
                role = Role.Button
            )
            .padding(contentPadding),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent?.let { it() }
        content()
        trailingContent?.let { it() }
    }
}
