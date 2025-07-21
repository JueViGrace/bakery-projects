package com.bakery.home.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.bakery.home.presentation.events.HomeEvents
import com.bakery.home.presentation.state.HomeState
import com.bakery.home.presentation.ui.components.layout.HomeScaffold
import com.bakery.home.presentation.viewmodel.HomeViewModel
import com.bakery.ui.components.containers.ScaffoldContainer
import com.bakery.ui.components.navigation.delayedComposable
import com.bakery.ui.components.observable.ObserveAsEvents
import com.bakery.ui.navigation.HomeTabGraphRoute
import com.bakery.ui.navigation.HomeTabRoute
import com.bakery.ui.navigation.NavigationAction
import com.bakery.ui.navigation.OrdersTabRoute
import com.bakery.ui.navigation.ProfileTabRoute
import com.bakery.ui.navigation.navigator.LocalTabNavigator
import com.bakery.ui.navigation.tab.TabNavigator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state: HomeState by viewModel.state.collectAsStateWithLifecycle()
    val bottomNavController: NavHostController = rememberNavController()
    val tabNavigator: TabNavigator = LocalTabNavigator.current

    ObserveAsEvents(
        bottomNavController.currentBackStackEntryFlow,
    ) { entry ->
        when (entry.destination.route) {
            HomeTabRoute::class.qualifiedName,
            OrdersTabRoute::class.qualifiedName,
            ProfileTabRoute::class.qualifiedName -> {}
            else -> {
                viewModel.onEvent(HomeEvents.HideBottomBar)
            }
        }
    }

    ObserveAsEvents(
        flow = tabNavigator.navigationActions,
    ) { action ->
        when (action) {
            is NavigationAction.Navigate -> {
                tabNavigator.consumeAction(action)
                bottomNavController.navigate(action.destination, navOptions = action.navOptions)
            }

            NavigationAction.NavigateUp -> {
                tabNavigator.consumeAction(action)
                bottomNavController.navigateUp()
            }
        }
    }

    ScaffoldContainer(
        scaffold = { _, snackbarHost, content ->
            HomeScaffold(
                state = state,
                onEvent = viewModel::onEvent,
                snackbarHost = snackbarHost,
                content = content,
            )
        },
    ) {
        NavHost(
            navController = bottomNavController,
            startDestination = HomeTabGraphRoute,
        ) {
            navigation<HomeTabGraphRoute>(
                startDestination = HomeTabRoute,
            ) {
                delayedComposable<HomeTabRoute> { _ ->
                    HomeContent(
                        state = state,
                        onEvent = viewModel::onEvent,
                    )
                }
                delayedComposable<OrdersTabRoute> { _ ->
                }
                delayedComposable<ProfileTabRoute> { _ ->
                }
            }
        }
    }
}
