package com.bakery.home.presentation.ui.components.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.bakery.cart.presentation.ui.components.navigation.entries.cartNav
import com.bakery.cart.presentation.ui.screen.CartScreen
import com.bakery.home.presentation.ui.components.navigation.entries.homeNav
import com.bakery.home.presentation.ui.screen.HomeScreen
import com.bakery.notification.presentation.ui.components.navigation.entries.notificationNav
import com.bakery.notification.presentation.ui.screen.NotificationScreen
import com.bakery.order.presentation.detail.ui.components.navigation.entries.orderDetailNav
import com.bakery.order.presentation.detail.ui.screen.OrderDetailsScreen
import com.bakery.ui.navigation.HomeGraphRoute
import com.bakery.ui.navigation.HomeRoute

/**
 * Home navigation graph
 * */
fun NavGraphBuilder.homeGraph() {
    navigation<HomeGraphRoute>(
        startDestination = HomeRoute,
    ) {
        homeNav {
            HomeScreen()
        }

        orderDetailNav { orderId ->
            OrderDetailsScreen(orderId)
        }

        cartNav {
            CartScreen()
        }

        notificationNav {
            NotificationScreen()
        }
    }
}
