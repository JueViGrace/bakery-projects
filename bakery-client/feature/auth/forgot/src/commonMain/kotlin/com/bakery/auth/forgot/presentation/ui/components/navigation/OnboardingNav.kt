package com.bakery.auth.forgot.presentation.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bakery.ui.navigation.ForgotPasswordRoute

fun NavGraphBuilder.forgotNav(content: @Composable () -> Unit) {
    composable<ForgotPasswordRoute> { _ ->
        content()
    }
}
