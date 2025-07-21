package com.bakery.ui.navigation.tab

import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.home
import com.bakery.resources.generated.resources.ic_account
import com.bakery.resources.generated.resources.ic_home
import com.bakery.resources.generated.resources.ic_receipt
import com.bakery.resources.generated.resources.orders
import com.bakery.resources.generated.resources.profile
import com.bakery.ui.navigation.Destination
import com.bakery.ui.navigation.HomeTabRoute
import com.bakery.ui.navigation.OrdersTabRoute
import com.bakery.ui.navigation.ProfileTabRoute
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class Tab(open val route: Destination, open val icon: DrawableResource, open val title: StringResource)

sealed class BottomTabs(
    override val route: Destination,
    override val icon: DrawableResource,
    override val title: StringResource,
) : Tab(
    route = route,
    icon = icon,
    title = title,
) {
    data object HomeTab : BottomTabs(
        route = HomeTabRoute,
        icon = Res.drawable.ic_home,
        title = Res.string.home,
    )
    data object OrdersTab : BottomTabs(
        route = OrdersTabRoute,
        icon = Res.drawable.ic_receipt,
        title = Res.string.orders,
    )
    data object ProfileTab : BottomTabs(
        route = ProfileTabRoute,
        icon = Res.drawable.ic_account,
        title = Res.string.profile,
    )

    companion object {
        val defaultList: List<Tab> = listOf(
            HomeTab,
            OrdersTab,
            ProfileTab,
        )
    }
}
