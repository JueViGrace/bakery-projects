package com.bakery.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakery.cart.data.CartRepository
import com.bakery.cart.presentation.events.CartEvents
import com.bakery.cart.presentation.state.CartState
import com.bakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel(
    private val repository: CartRepository
) : BaseViewModel, ViewModel() {
    override val scope: CoroutineScope = viewModelScope

    private val _state: MutableStateFlow<CartState> = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    fun onEvent(event: CartEvents) {
        when (event) {
            CartEvents.OnCheckout -> checkout()
        }
    }

    private fun checkout() {
    }
}
