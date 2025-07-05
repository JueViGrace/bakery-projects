package com.bakery.home.presentation.ui.components.navigation.entries

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bakery.ui.navigation.HomeRoute

fun NavGraphBuilder.homeNav(
    content: @Composable () -> Unit
) {
    composable<HomeRoute> { _ ->
        content()
    }
}
