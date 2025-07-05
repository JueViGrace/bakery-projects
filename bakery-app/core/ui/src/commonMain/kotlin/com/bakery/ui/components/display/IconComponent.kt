package com.bakery.ui.components.display

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconComponent(
    modifier: Modifier = Modifier.size(24.dp),
    painter: Painter,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) {
    Icon(
        modifier = modifier,
        painter = painter,
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IconComponent(
    modifier: Modifier = Modifier.size(24.dp),
    imageVector: ImageVector,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IconComponent(
    modifier: Modifier = Modifier.size(24.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    painter: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    tint: Color = LocalContentColor.current
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        Icon(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            painter = painter,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun IconComponent(
    modifier: Modifier = Modifier.size(24.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    tint: Color = LocalContentColor.current
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        Icon(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}
