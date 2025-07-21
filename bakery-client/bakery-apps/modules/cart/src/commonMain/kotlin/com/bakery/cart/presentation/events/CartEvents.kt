package com.bakery.cart.presentation.events

sealed interface CartEvents {
    data object OnCheckout : CartEvents
}
