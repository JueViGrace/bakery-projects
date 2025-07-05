package com.bakery.ui.model

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Immutable
data class RowDisplayType(
    val leadingIcon: DrawableResource? = null,
    val title: StringResource,
    val trailingIcon: DrawableResource? = null,
)
