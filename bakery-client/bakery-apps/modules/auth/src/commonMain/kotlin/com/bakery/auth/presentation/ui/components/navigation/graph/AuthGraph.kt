package com.bakery.auth.presentation.ui.components.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.bakery.auth.forgot.presentation.ui.components.navigation.forgotNav
import com.bakery.auth.forgot.presentation.ui.screen.ForgotScreen
import com.bakery.auth.onboarding.presentation.ui.components.navigation.onboardingNav
import com.bakery.auth.onboarding.presentation.ui.screen.OnboardingScreen
import com.bakery.auth.signin.presentation.ui.components.navigation.signInNav
import com.bakery.auth.signin.presentation.ui.screen.SignInScreen
import com.bakery.auth.signup.presentation.ui.components.navigation.signUpNav
import com.bakery.auth.signup.presentation.ui.screen.SignUpScreen
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
        forgotNav { action ->
            ForgotScreen()
        }
    }
}
