package com.bakery.ui.navigation.navigator

import androidx.navigation.NavOptions
import com.bakery.ui.navigation.ActionStack
import com.bakery.ui.navigation.Destination
import com.bakery.ui.navigation.NavigationAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

internal object NavigatorImpl : Navigator {
    private val _navigationActions: Channel<NavigationAction> = Channel<NavigationAction>()
    override val navigationActions: Flow<NavigationAction> = _navigationActions.receiveAsFlow()

    private val _actionStack: MutableStateFlow<ActionStack> = MutableStateFlow(ActionStack())
    override val actions: StateFlow<ActionStack> = _actionStack.asStateFlow()

    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptions?,
    ) {
        val action = NavigationAction.Navigate(destination, navOptions)
        if (!_actionStack.value.actions.contains(action)) {
            _actionStack.update { actions ->
                actions.addAction(action)
            }
            _navigationActions.send(action)
        }
    }

    override suspend fun navigateUp() {
        val action: NavigationAction.NavigateUp = NavigationAction.NavigateUp
        if (!_actionStack.value.actions.contains(action)) {
            _actionStack.update { actions ->
                actions.addAction(action)
            }
            delay(250)
            _navigationActions.send(action)
        }
    }

    override fun consumeAction(action: NavigationAction) {
        _actionStack.update { state ->
            state.removeAction(action)
        }
    }
}
