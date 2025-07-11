package com.bakery.core.presentation.ui.components.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun LetterComponent(
    modifier: Modifier = Modifier,
    letter: String,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    fontWeight: FontWeight? = MaterialTheme.typography.titleLarge.fontWeight,
) {
    Box(
        modifier =
            modifier
                .size(50.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape,
                ).clip(CircleShape)
                .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        TextComponent(
            text = letter,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}
