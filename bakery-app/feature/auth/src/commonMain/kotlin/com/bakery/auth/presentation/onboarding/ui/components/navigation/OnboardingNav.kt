package com.bakery.auth.presentation.onboarding.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bakery.ui.navigation.OnboardingRoute

fun NavGraphBuilder.onboardingNav(
    content: @Composable () -> Unit
) {
    composable<OnboardingRoute> { _ ->
        content()
    }
}
