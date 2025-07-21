package com.bakery.app.presentation.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bakery.ui.components.observable.ObserveAsEvents
import com.bakery.ui.navigation.NavigationAction
import com.bakery.ui.navigation.navigator.LocalNavController
import com.bakery.ui.navigation.navigator.LocalNavigator
import com.bakery.ui.navigation.navigator.Navigator

@Composable
fun Navigation(
    performAction: (action: NavigationAction) -> Unit = { _ -> },
    routes: NavGraphBuilder.(navigator: Navigator) -> Unit
) {
    val navigator: Navigator = LocalNavigator.current
    val navController: NavHostController = LocalNavController.current

    ObserveAsEvents(
        flow = navigator.navigationActions,
    ) { action ->
        when (action) {
            is NavigationAction.Navigate -> {
                performAction(action)
                navigator.consumeAction(action)
                navController.navigate(action.destination, navOptions = action.navOptions)
            }

            NavigationAction.NavigateUp -> {
                navigator.consumeAction(action)
                navController.navigateUp()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startDestination,
    ) {
        routes(navigator)
    }
}
