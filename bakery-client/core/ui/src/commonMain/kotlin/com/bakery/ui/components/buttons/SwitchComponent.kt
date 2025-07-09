package com.bakery.ui.components.buttons

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SwitchComponent(
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
) {
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChanged,
        colors = colors,
        thumbContent = thumbContent,
        enabled = enabled,
    )
}
