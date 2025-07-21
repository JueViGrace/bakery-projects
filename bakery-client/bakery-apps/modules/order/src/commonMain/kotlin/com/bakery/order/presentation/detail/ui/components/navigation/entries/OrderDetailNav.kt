package com.bakery.order.presentation.detail.ui.components.navigation.entries

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bakery.ui.navigation.OrderDetailRoute

fun NavGraphBuilder.orderDetailNav(content: @Composable (orderId: String) -> Unit) {
    composable<OrderDetailRoute> { backStackEntry ->
        val params: OrderDetailRoute = backStackEntry.toRoute()
        content(params.orderId)
    }
}
