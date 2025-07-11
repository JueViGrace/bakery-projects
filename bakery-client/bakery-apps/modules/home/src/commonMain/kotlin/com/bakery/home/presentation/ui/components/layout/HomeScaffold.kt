package com.bakery.home.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bakery.home.presentation.events.HomeEvents
import com.bakery.home.presentation.state.HomeState
import com.bakery.home.presentation.ui.components.layout.bars.HomeTopBar
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.account
import com.bakery.resources.generated.resources.ic_account
import com.bakery.resources.generated.resources.ic_bell
import com.bakery.resources.generated.resources.ic_grid_dots
import com.bakery.resources.generated.resources.ic_search
import com.bakery.resources.generated.resources.notifications
import com.bakery.resources.generated.resources.search
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.layout.bars.bottom.BottomNavBar
import com.bakery.ui.components.layout.scaffold.DefaultScaffold
import com.bakery.ui.layout.bars.NavBars
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(
    state: HomeState,
    onEvent: (HomeEvents) -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    DefaultScaffold(
        topBar = {
            HomeTopBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(HomeEvents.OnAccount)
                        },
                    ) {
                        IconComponent(
                            painter = painterResource(Res.drawable.ic_account),
                            contentDescription = stringResource(Res.string.account),
                        )
                    }
                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = state.user?.name ?: "",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(HomeEvents.OnNotifications)
                        },
                    ) {
                        IconComponent(
                            painter = painterResource(Res.drawable.ic_bell),
                            contentDescription = stringResource(Res.string.notifications),
                        )
                    }

                    IconButton(
                        onClick = {
                            onEvent(HomeEvents.ToggleSearch)
                        },
                    ) {
                        IconComponent(
                            painter = painterResource(Res.drawable.ic_search),
                            contentDescription = stringResource(Res.string.search),
                            onClick = {
                                onEvent(HomeEvents.ToggleSearch)
                            },
                        )
                    }
                },
                onTabSelected = { index ->
                    onEvent(HomeEvents.OnTabSelected(index, NavBars.TopBar))
                },
            )
        },
        bottomBar = {
            BottomNavBar(
                state = state.bottomBarState,
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            onEvent(HomeEvents.ToggleMore)
                        },
                    ) {
                        IconComponent(
                            painter = painterResource(Res.drawable.ic_grid_dots),
                        )
                    }
                },
                onTabSelected = { index ->
                    onEvent(HomeEvents.OnTabSelected(index, NavBars.BottomBar))
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = snackbarHost,
        content = content,
    )
}
