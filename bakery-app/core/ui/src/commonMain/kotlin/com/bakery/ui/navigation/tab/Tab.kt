package com.bakery.ui.navigation.tab

import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.home
import com.bakery.resources.generated.resources.ic_home
import com.bakery.ui.navigation.Destination
import com.bakery.ui.navigation.HomeRoute
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class Tab(open val route: Destination, open val icon: DrawableResource, open val title: StringResource)

sealed class BottomTabs(override val route: Destination, override val icon: DrawableResource, override val title: StringResource) :
    Tab(
        route = route,
        icon = icon,
        title = title,
    ) {
    data object HomeTab : BottomTabs(
        route = HomeRoute,
        icon = Res.drawable.ic_home,
        title = Res.string.home,
    )
}
