package com.bakery.cart.presentation.ui.components.navigation.entries

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bakery.ui.navigation.CartRoute

fun NavGraphBuilder.cartNav(content: @Composable () -> Unit) {
    composable<CartRoute> { _ ->
        content()
    }
}
