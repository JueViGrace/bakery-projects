package com.bakery.auth.presentation.ui.components.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.bakery.auth.presentation.onboarding.ui.components.navigation.onboardingNav
import com.bakery.auth.presentation.onboarding.ui.screen.OnboardingScreen
import com.bakery.auth.presentation.signin.ui.components.navigation.signInNav
import com.bakery.auth.presentation.signin.ui.screen.SignInScreen
import com.bakery.auth.presentation.signup.ui.components.navigation.signUpNav
import com.bakery.auth.presentation.signup.ui.screen.SignUpScreen
import com.bakery.ui.navigation.AuthGraphRoute
import com.bakery.ui.navigation.SignInRoute

fun NavGraphBuilder.authGraph() {
    navigation<AuthGraphRoute>(
        startDestination = SignInRoute,
    ) {
        onboardingNav {
            OnboardingScreen()
        }
        signInNav {
            SignInScreen()
        }
        signUpNav {
            SignUpScreen()
        }
    }
}
