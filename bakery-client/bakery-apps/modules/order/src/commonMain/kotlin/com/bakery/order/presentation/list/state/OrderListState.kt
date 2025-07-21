package com.bakery.order.presentation.state

import com.bakery.types.order.Order

data class OrderListState(val orders: List<Order> = emptyList())
