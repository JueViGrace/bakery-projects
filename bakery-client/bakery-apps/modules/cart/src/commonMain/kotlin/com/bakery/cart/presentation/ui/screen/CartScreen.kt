package com.bakery.cart.presentation.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.cart.presentation.events.CartEvents
import com.bakery.cart.presentation.state.CartState
import com.bakery.cart.presentation.viewmodel.CartViewModel
import com.bakery.ui.components.buttons.BackButton
import com.bakery.ui.components.containers.ScreenContainer
import com.bakery.ui.components.layout.bars.top.TopBarComponent
import com.bakery.ui.components.layout.scaffold.DefaultScaffold
import com.bakery.ui.navigation.navigator.LocalNavigator
import com.bakery.ui.navigation.navigator.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel = koinViewModel()
) {
    val state: CartState by viewModel.state.collectAsStateWithLifecycle()

    CartContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onEvent: (CartEvents) -> Unit = {}
) {
    val navigator: Navigator = LocalNavigator.current
    val scope: CoroutineScope = rememberCoroutineScope()

    ScreenContainer(
        scaffold = { contentWindowInsets, snackbarHost, content ->
            DefaultScaffold(
                topBar = {
                    TopBarComponent(
                        navigationIcon = {
                            BackButton {
                                scope.launch {
                                    navigator.navigateUp()
                                }
                            }
                        }
                    )
                },
                contentWindowInsets = contentWindowInsets,
                snackbarHost = snackbarHost,
                content = content
            )
        }
    ) {
    }
}
