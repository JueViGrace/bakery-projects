package com.bakery.ui.components.display.table

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.bakery.ui.Fonts

@Composable
fun<T> TableCell(
    modifier: Modifier = Modifier,
    value: T,
    color: Color = LocalContentColor.current,
    style: TextStyle = Fonts.smallTextStyle,
    textAlign: TextAlign = TextAlign.Start
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$value",
            maxLines = 1,
            style = style,
            textAlign = textAlign,
            color = color
        )
    }
}
