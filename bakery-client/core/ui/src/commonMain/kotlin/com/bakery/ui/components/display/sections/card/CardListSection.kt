package com.bakery.ui.components.display.sections.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.ui.components.containers.cards.ElevatedCardFlowContainer
import com.bakery.ui.components.containers.cards.ElevatedCardFlowContainerIndexed
import com.bakery.ui.components.display.sections.SectionTitle

@Composable
fun <T> CardListSection(modifier: Modifier = Modifier, title: String = "", items: List<T>, content: @Composable (item: T) -> Unit) {
    SectionTitle(
        modifier = modifier,
        title = title,
        titlePadding = PaddingValues(6.dp),
    ) {
        ElevatedCardFlowContainer(
            items = items,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            contentPadding = PaddingValues(16.dp),
            separator = false,
        ) { item ->
            content(item)
        }
    }
}

@Composable
fun <T> CardListSectionIndexed(
    modifier: Modifier = Modifier,
    title: String = "",
    items: List<T>,
    content: @Composable (index: Int, item: T) -> Unit,
) {
    SectionTitle(
        modifier = modifier,
        title = title,
        titlePadding = PaddingValues(6.dp),
    ) {
        ElevatedCardFlowContainerIndexed(
            items = items,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            contentPadding = PaddingValues(16.dp),
            separator = false,
        ) { index, item ->
            content(index, item)
        }
    }
}
