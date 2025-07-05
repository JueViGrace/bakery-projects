package com.bakery.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class TableData<H, T>(
    val rows: List<TableRow<H, T>> = emptyList(),
)

@Immutable
data class TableRow<H, T>(
    val cells: Map<H, TableCell<T>> = emptyMap(),
    val isHeader: Boolean = false,
)

@Immutable
data class TableCell<T>(
    val value: T,
)
