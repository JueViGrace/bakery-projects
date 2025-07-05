package com.bakery.home.presentation.ui.components.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.bakery.home.presentation.ui.components.navigation.entries.homeNav
import com.bakery.home.presentation.ui.screen.HomeScreen
import com.bakery.ui.navigation.HomeGraphRoute
import com.bakery.ui.navigation.HomeRoute

/**
 * Home navigation graph
 * */
fun NavGraphBuilder.homeGraph() {
    navigation<HomeGraphRoute>(
        startDestination = HomeRoute
    ) {
        homeNav {
            HomeScreen()
        }
    }
}
