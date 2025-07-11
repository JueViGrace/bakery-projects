package com.bakery.ui.components.display.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bakery.ui.components.display.RowDisplay
import com.bakery.ui.model.TableCell

@Composable
fun <H, T> TableRow(
    cells: Map<H, TableCell<T>>,
    modifier: Modifier = Modifier,
    isHeader: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    cellContent: @Composable (type: H, value: TableCell<T>) -> Unit,
) {
    RowDisplay(
        modifier = modifier
            .background(
                color = if (isHeader) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    Color.Unspecified
                },
            ),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        cells.forEach { cell ->
            cellContent(cell.key, cell.value)
        }
    }
}

@Composable
fun <H, T> TableRow(
    cells: Map<H, TableCell<T>>,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    isHeader: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    cellContent: @Composable (type: H, value: TableCell<T>) -> Unit,
) {
    RowDisplay(
        modifier = Modifier
            .background(
                color = if (isHeader) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    Color.Unspecified
                },
            )
            .then(modifier),
        onClick = onClick,
        contentPadding = contentPadding,
    ) {
        cells.forEach { cell ->
            cellContent(cell.key, cell.value)
        }
    }
}
