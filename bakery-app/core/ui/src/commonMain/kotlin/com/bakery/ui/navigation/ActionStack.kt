package com.bakery.ui.navigation

data class ActionStack(
    val actions: List<NavigationAction> = emptyList()
) {
    fun addAction(action: NavigationAction): ActionStack {
        val actions: MutableList<NavigationAction> = actions.toMutableList()

        if (!actions.contains(action)) {
            actions.add(action)
        }

        return copy(actions = actions)
    }

    fun removeAction(action: NavigationAction): ActionStack {
        val actions: MutableList<NavigationAction> = actions.toMutableList()
        actions.remove(action)
        return copy(actions = actions)
    }
}
