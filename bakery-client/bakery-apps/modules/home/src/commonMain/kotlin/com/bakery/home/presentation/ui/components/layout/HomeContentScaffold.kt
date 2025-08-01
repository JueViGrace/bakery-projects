package com.bakery.home.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.home.presentation.events.HomeEvents
import com.bakery.home.presentation.state.HomeState
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.cart
import com.bakery.resources.generated.resources.ic_bell
import com.bakery.resources.generated.resources.ic_shopping_cart
import com.bakery.resources.generated.resources.logo
import com.bakery.resources.generated.resources.notifications
import com.bakery.resources.generated.resources.reimu
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.ImageComponent
import com.bakery.ui.components.layout.bars.top.TopNavBar
import com.bakery.ui.components.layout.scaffold.DefaultScaffold
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeContentScaffold(
    state: HomeState,
    onEvent: (HomeEvents) -> Unit = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    DefaultScaffold(
        topBar = {
            if (state.innerTopBarState.showBar) {
                TopNavBar(
                    navigationIcon = {
                        ImageComponent(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(24.dp),
                            painter = painterResource(Res.drawable.reimu),
                            contentDescription = stringResource(Res.string.logo),
                        )
                    },
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(
                                onClick = {
                                    onEvent(HomeEvents.OnNotifications)
                                }
                            ) {
                                IconComponent(
                                    painter = painterResource(Res.drawable.ic_bell),
                                    contentDescription = stringResource(Res.string.notifications)
                                )
                            }

                            IconButton(
                                onClick = {
                                    onEvent(HomeEvents.OnCart)
                                }
                            ) {
                                IconComponent(
                                    painter = painterResource(Res.drawable.ic_shopping_cart),
                                    contentDescription = stringResource(Res.string.cart)
                                )
                            }
                        }
                    }
                )
            }
        },
        contentWindowInsets = contentWindowInsets,
        content = content,
    )
}
