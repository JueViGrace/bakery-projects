package com.bakery.ui.components.containers.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@Composable
fun <T> CardFlowContainer(
    modifier: Modifier = Modifier,
    items: List<T> = emptyList(),
    separator: Boolean = true,
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceEvenly,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable (item: T) -> Unit,
) {
    Card(
        modifier = modifier,
    ) {
        FlowColumn(
            modifier = Modifier.fillMaxWidth().padding(contentPadding),
            verticalArrangement = verticalArrangement,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {
            items.fastForEachIndexed { index, item ->
                content(item)

                if (index != items.lastIndex && separator) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun <T> CardFlowContainerIndexed(
    modifier: Modifier = Modifier,
    items: List<T> = emptyList(),
    separator: Boolean = true,
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceEvenly,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable (index: Int, item: T) -> Unit,
) {
    Card(
        modifier = modifier,
    ) {
        FlowColumn(
            modifier = Modifier.fillMaxWidth().padding(contentPadding),
            verticalArrangement = verticalArrangement,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {
            items.fastForEachIndexed { index, item ->
                content(index, item)

                if (index != items.lastIndex && separator) {
                    HorizontalDivider()
                }
            }
        }
    }
}
