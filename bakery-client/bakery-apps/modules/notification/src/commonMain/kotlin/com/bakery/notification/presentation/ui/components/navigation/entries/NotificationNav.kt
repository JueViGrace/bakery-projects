package com.bakery.notification.presentation.ui.components.navigation.entries

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bakery.ui.navigation.NotificationRoute

fun NavGraphBuilder.notificationNav(content: @Composable () -> Unit) {
    composable<NotificationRoute> { _ ->
        content()
    }
}
