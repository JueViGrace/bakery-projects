package com.bakery.ui.components.containers.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun CardContainer(
    modifier: Modifier = Modifier,
    shapes: Shape = CardDefaults.elevatedShape,
    elevation: CardElevation = CardDefaults.elevatedCardElevation(),
    colors: CardColors = CardDefaults.elevatedCardColors(),
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceAround,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            shape = shapes,
            elevation = elevation,
            colors = colors
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding),
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
            ) {
                content()
            }
        }
    }
}
