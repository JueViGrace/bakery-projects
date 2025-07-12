package com.bakery.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthFormSection(
    fields: @Composable FlowColumnScope.() -> Unit,
    submitContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    maxItemsInEachColumn: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FlowColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = verticalArrangement,
            horizontalArrangement = horizontalArrangement,
            itemHorizontalAlignment = itemHorizontalAlignment,
            maxItemsInEachColumn = maxItemsInEachColumn,
            maxLines = maxLines,
        ) {
            fields()
        }
        submitContent()
    }
}
