package com.bakery.order.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.order.presentation.detail.state.OrderDetailsState
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderDetailsViewModel : BaseViewModel, ViewModel() {
    override val scope: CoroutineScope = viewModelScope

    private val _state = MutableStateFlow(OrderDetailsState())
    val state = _state.asStateFlow()
}
