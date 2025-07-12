package com.bakery.ui.components.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.ui.components.display.RowDisplay



@Composable
fun RowCardButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
    ) {
        ElevatedCard(
            onClick = onClick,
            colors = colors,
            enabled = enabled,
        ) {
            RowDisplay(
                contentPadding = contentPadding,
                leadingContent = leadingIcon,
                trailingContent = trailingIcon,
                content = content,
            )
        }
    }
}
