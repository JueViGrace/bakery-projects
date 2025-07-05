package com.bakery.ui.components.containers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue

@Composable
fun ProvidersContainer(
    vararg values: ProvidedValue<*>,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(values = values) {
        content()
    }
}
