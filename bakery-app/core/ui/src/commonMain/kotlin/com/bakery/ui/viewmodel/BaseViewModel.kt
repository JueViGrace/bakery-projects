package com.bakery.ui.viewmodel

import androidx.navigation.NavOptions
import com.bakery.ui.messages.Messages
import com.bakery.ui.navigation.Destination
import com.bakery.ui.navigation.navigator.Navigator
import com.bakery.ui.navigation.tab.TabNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

interface BaseViewModel {
    val scope: CoroutineScope

    val navigator: Navigator
        get() = Navigator.Companion.instance

    val tabNavigator: TabNavigator
        get() = TabNavigator.instance

    val messages: Messages
        get() = Messages.Companion.instance

    fun navigateTo(destination: Destination, navOptions: NavOptions? = null) {
        scope.launch {
            navigator.navigate(
                destination = destination,
                navOptions = navOptions,
            )
        }
    }

    fun navigateBack() {
        scope.launch {
            navigator.navigateUp()
        }
    }

    fun sendMessage(message: StringResource, description: String? = null) {
        scope.launch {
            messages.sendMessage(
                message = message,
                description = description,
            )
        }
    }
}
