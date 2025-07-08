package com.bakery.app.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.composable
import com.bakery.app.presentation.ui.components.navigation.Navigation
import com.bakery.app.presentation.ui.screens.splash.SplashScreen
import com.bakery.app.presentation.viewmodel.AppViewModel
import com.bakery.auth.presentation.ui.components.navigation.graph.authGraph
import com.bakery.home.presentation.ui.components.navigation.graph.homeGraph
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.compose_multiplatform
import com.bakery.ui.navigation.AuthGraphRoute
import com.bakery.ui.navigation.NavigationAction
import com.bakery.ui.navigation.SplashRoute
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()

    Navigation(
        performAction = { action ->
            if (action is NavigationAction.Navigate && action.destination is AuthGraphRoute) {
                viewModel.endSession()
            }
        },
    ) { _ ->
        composable<SplashRoute> { _ ->
            LaunchedEffect(Unit) {
                viewModel.checkSession()
            }

            SplashScreen(
                painter = painterResource(Res.drawable.compose_multiplatform)
            )
        }
        authGraph()
        homeGraph()
    }
}
