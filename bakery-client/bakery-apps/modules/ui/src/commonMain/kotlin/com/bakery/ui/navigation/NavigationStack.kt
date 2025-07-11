package com.bakery.ui.navigation

data class NavigationStack(val destinations: List<Destination> = emptyList(), val currentDestination: Destination? = null)
