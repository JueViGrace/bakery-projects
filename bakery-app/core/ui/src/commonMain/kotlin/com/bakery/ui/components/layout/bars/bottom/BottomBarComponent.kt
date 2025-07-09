package com.bakery.ui.components.layout.bars.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.navigation.tab.Tab
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomBarComponent(
    tabs: List<Tab> = emptyList(),
    selected: (tab: Tab) -> Boolean = { false },
    floatingActionButton: (@Composable () -> Unit)? = null,
    onTabSelected: (index: Int) -> Unit,
) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEachIndexed { index, destination ->
                    BottomTabItem(
                        label = {
                            Text(
                                text = stringResource(destination.title),
                                style = Fonts.labelTextStyle,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        icon = {
                            IconComponent(
                                painter = painterResource(destination.icon),
                            )
                        },
                        selected = selected(destination),
                        onClick = {
                            onTabSelected(index)
                        },
                    )
                }
            }
        },
        floatingActionButton = floatingActionButton,
    )
}
