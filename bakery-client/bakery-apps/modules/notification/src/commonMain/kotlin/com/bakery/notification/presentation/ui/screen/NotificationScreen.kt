package com.bakery.notification.presentation.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.notification.presentation.events.NotificationEvents
import com.bakery.notification.presentation.state.NotificationState
import com.bakery.notification.presentation.viewmodel.NotificationViewModel
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
fun NotificationScreen(
    viewModel: NotificationViewModel = koinViewModel()
) {
    val state: NotificationState by viewModel.state.collectAsStateWithLifecycle()

    NotificationContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationContent(
    state: NotificationState,
    onEvent: (NotificationEvents) -> Unit = {}
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
