package com.bakery.auth.signup.presentation.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bakery.ui.navigation.SignUpRoute

fun NavGraphBuilder.signUpNav(content: @Composable () -> Unit) {
    composable<SignUpRoute> { _ ->
        content()
    }
}
