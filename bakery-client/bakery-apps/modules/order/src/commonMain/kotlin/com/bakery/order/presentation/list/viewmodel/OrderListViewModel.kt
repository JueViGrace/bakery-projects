package com.bakery.order.presentation.list.viewmodel

import androidx.lifecycle.ViewModel
import com.bakery.order.presentation.state.OrderListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderListViewModel : ViewModel() {
    private val _state = MutableStateFlow(OrderListState())
    val state = _state.asStateFlow()
}
