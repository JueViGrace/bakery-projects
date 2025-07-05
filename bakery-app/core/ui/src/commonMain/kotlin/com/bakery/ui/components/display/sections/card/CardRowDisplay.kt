package com.bakery.ui.components.display.sections.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.ui.components.display.RowDisplay
import com.bakery.ui.model.RowDisplayType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CardRowDisplay(
    modifier: Modifier = Modifier,
    item: RowDisplayType,
    onClick: () -> Unit,
    leadingIcon: @Composable (DrawableResource) -> Unit = {},
    trailingIcon: @Composable (DrawableResource) -> Unit = {},
) {
    Card(
        modifier = modifier
    ) {
        RowDisplay(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            contentPadding = PaddingValues(8.dp),
            leadingContent = item.leadingIcon?.let { icon ->
                {
                    leadingIcon(icon)
                }
            },
            content = {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(item.title),
                    maxLines = 1,
                )
            },
            trailingContent = item.trailingIcon?.let { icon ->
                {
                    trailingIcon(icon)
                }
            },
            onClick = onClick
        )
    }
}
