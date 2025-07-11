package com.bakery.ui.components.display.table

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.ui.components.containers.cards.CardFlowContainer
import com.bakery.ui.components.containers.cards.CardFlowContainerIndexed
import com.bakery.ui.model.TableCell
import com.bakery.ui.model.TableData
import com.bakery.ui.model.TableRow

// todo: change row display by column display?
@Composable
fun <H, T> CardTable(
    data: TableData<H, T>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    cellContent: @Composable (isHeader: Boolean, type: H, value: TableCell<T>) -> Unit,
) {
    CardFlowContainer(
        modifier = modifier,
        items = data.rows,
        separator = false,
    ) { row ->
        com.bakery.ui.components.display.table.TableRow(
            modifier = Modifier.fillMaxWidth(),
            cells = row.cells,
            isHeader = row.isHeader,
            contentPadding = contentPadding,
            cellContent = { type, cell ->
                cellContent(row.isHeader, type, cell)
            },
        )
    }
}

@Composable
fun <H, T> CardTable(
    data: TableData<H, T>,
    onRowClick: (item: TableRow<H, T>) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    cellContent: @Composable (isHeader: Boolean, type: H, value: TableCell<T>) -> Unit,
) {
    CardFlowContainer(
        modifier = modifier,
        items = data.rows,
        separator = false,
    ) { row ->
        TableRow(
            modifier = Modifier.fillMaxWidth(),
            cells = row.cells,
            isHeader = row.isHeader,
            contentPadding = contentPadding,
            onClick = {
                onRowClick(row)
            },
            cellContent = { type, cell ->
                cellContent(row.isHeader, type, cell)
            },
        )
    }
}

@Composable
fun <H, T> CardTableIndexed(
    data: TableData<H, T>,
    onRowClick: (index: Int, item: TableRow<H, T>) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    cellContent: @Composable (isHeader: Boolean, type: H, value: TableCell<T>) -> Unit,
) {
    CardFlowContainerIndexed(
        modifier = modifier,
        items = data.rows,
        separator = false,
        contentPadding = contentPadding,
    ) { index, row ->
        TableRow(
            modifier = Modifier.fillMaxWidth(),
            cells = row.cells,
            isHeader = row.isHeader,
            contentPadding = contentPadding,
            onClick = {
                onRowClick(index, row)
            },
            cellContent = { type, cell ->
                cellContent(row.isHeader, type, cell)
            },
        )
    }
}
