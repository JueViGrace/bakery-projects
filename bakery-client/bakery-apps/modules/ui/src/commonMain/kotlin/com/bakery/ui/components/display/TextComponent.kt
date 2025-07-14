package com.bakery.ui.components.display

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextComponent(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    style: TextStyle = TextStyle(
        color = color,
    ),
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    autoSize: TextAutoSize = TextAutoSize.StepBased(minFontSize = 14.sp, maxFontSize = 24.sp)
) {
    BasicText(
        modifier = modifier,
        text = text,
        style = style,
        maxLines = maxLines,
        minLines = minLines,
        overflow = overflow,
        softWrap = softWrap,
        autoSize = autoSize
    )
}
