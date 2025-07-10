package com.bakery.ui.components.layout.bars.top

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.ui.Fonts
import com.bakery.ui.layout.bars.NavBarState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    state: NavBarState = remember { NavBarState() },
    navigationIcon: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = state.showBar,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBarComponent(
                    navigationIcon = navigationIcon,
                    title = title,
                    actions = {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            actions()
                        }
                    },
                )
                if (state.tabs.isNotEmpty()) {
                    SecondaryTabRow(
                        selectedTabIndex = state.selectedTabIndex,
                    ) {
                        state.tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = tab == state.selectedTab,
                                onClick = {
                                    onTabSelected(index)
                                },
                                text = {
                                    Text(
                                        text = stringResource(tab.title),
                                        style = Fonts.smallTextStyle,
                                        maxLines = 1,
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
