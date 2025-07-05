package com.bakery.ui.navigation

import kotlinx.serialization.Serializable

/*
* Destinations used throughout the project.
* */
@Serializable
sealed interface Destination {
    @Serializable
    data object Splash : Destination

    @Serializable
    sealed interface AuthGraph : Destination {
        @Serializable
        data object Graph : AuthGraph

        @Serializable
        data object Onboarding : AuthGraph

        @Serializable
        data object SignIn : AuthGraph

        @Serializable
        data object SignUp : AuthGraph

        @Serializable
        data object ForgotPassword : AuthGraph
    }

    @Serializable
    sealed interface HomeGraph : Destination {
        @Serializable
        data object Graph : HomeGraph

        @Serializable
        data object Home : HomeGraph

        @Serializable
        sealed interface TabGraph : HomeGraph {
            @Serializable
            data object Graph : TabGraph

            @Serializable
            sealed interface BottomGraph : TabGraph {
                @Serializable
                data object Dashboard : BottomGraph
            }
        }
    }
}

typealias SplashRoute = Destination.Splash

typealias AuthGraphRoute = Destination.AuthGraph.Graph
typealias OnboardingRoute = Destination.AuthGraph.Onboarding
typealias SignInRoute = Destination.AuthGraph.SignIn
typealias SignUpRoute = Destination.AuthGraph.SignUp
typealias ForgotPasswordRoute = Destination.AuthGraph.ForgotPassword

typealias HomeGraphRoute = Destination.HomeGraph.Graph
typealias HomeRoute = Destination.HomeGraph.Home
typealias HomeTabGraphRoute = Destination.HomeGraph.TabGraph.Graph

typealias DashboardRoute = Destination.HomeGraph.TabGraph.BottomGraph.Dashboard
