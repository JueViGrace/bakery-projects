package com.bakery.ui.navigation

import kotlinx.serialization.Serializable

/**
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
        data class Forgot(val action: String) : AuthGraph
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
                data object Home : BottomGraph

                @Serializable
                data object Orders : BottomGraph

                @Serializable
                data object Profile : BottomGraph
            }
        }

        @Serializable
        data object Cart : HomeGraph

        @Serializable
        data object Notifications : HomeGraph

        @Serializable
        data object Search : HomeGraph

        @Serializable
        data object PaymentMethods : HomeGraph
    }
}

typealias SplashRoute = Destination.Splash

typealias AuthGraphRoute = Destination.AuthGraph.Graph
typealias OnboardingRoute = Destination.AuthGraph.Onboarding
typealias SignInRoute = Destination.AuthGraph.SignIn
typealias SignUpRoute = Destination.AuthGraph.SignUp
typealias ForgotRoute = Destination.AuthGraph.Forgot

typealias HomeGraphRoute = Destination.HomeGraph.Graph
typealias HomeRoute = Destination.HomeGraph.Home
typealias HomeTabGraphRoute = Destination.HomeGraph.TabGraph.Graph

typealias HomeTabRoute = Destination.HomeGraph.TabGraph.BottomGraph.Home
typealias OrdersTabRoute = Destination.HomeGraph.TabGraph.BottomGraph.Orders
typealias ProfileTabRoute = Destination.HomeGraph.TabGraph.BottomGraph.Profile

typealias CartRoute = Destination.HomeGraph.Cart
typealias NotificationsRoute = Destination.HomeGraph.Notifications
typealias SearchRoute = Destination.HomeGraph.Search
typealias PaymentMethodsRoute = Destination.HomeGraph.PaymentMethods
