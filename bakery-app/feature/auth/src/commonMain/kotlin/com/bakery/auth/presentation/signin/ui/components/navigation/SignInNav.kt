package com.bakery.auth.presentation.signin.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bakery.ui.navigation.SignInRoute

fun NavGraphBuilder.signInNav(
    content: @Composable () -> Unit
) {
    composable<SignInRoute> { _ ->
        content()
    }
}
