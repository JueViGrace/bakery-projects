package com.bakery.ui.components.layout.bars

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerNavItem(modifier: Modifier, title: String, icon: @Composable () -> Unit, selected: Boolean, onClick: () -> Unit) {
    NavigationDrawerItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = { Text(text = title) },
        shape = RoundedCornerShape(8.dp),
    )
}
