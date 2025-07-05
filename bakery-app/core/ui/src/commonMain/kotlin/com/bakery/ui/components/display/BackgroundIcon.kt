package com.bakery.ui.components.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundIcon(
    modifier: Modifier = Modifier.size(24.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    backgroundColor: Color = Color.Transparent,
    shape: Shape = CircleShape,
    painter: Painter,
    contentDescription: String? = null,
    tint: Color = contentColorFor(backgroundColor)
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .clip(shape),
        contentAlignment = Alignment.Center
    ) {
        IconComponent(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            painter = painter,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}
