package com.bakery.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.add
import com.bakery.resources.generated.resources.ic_chevron_down
import com.bakery.resources.generated.resources.ic_chevron_up
import com.bakery.resources.generated.resources.subtract
import com.bakery.ui.components.display.IconComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun IncrementDecrementButton(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    addEnabled: Boolean = true,
    subtractEnabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    enabled = addEnabled,
                    onClick = onIncrement,
                    onClickLabel = stringResource(Res.string.add),
                    role = Role.Button,
                )
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            IconComponent(
                painter = painterResource(Res.drawable.ic_chevron_up),
                contentDescription = stringResource(Res.string.add),
            )
        }
        Box(
            modifier = Modifier
                .clickable(
                    enabled = subtractEnabled,
                    onClick = onDecrement,
                    onClickLabel = stringResource(Res.string.subtract),
                    role = Role.Button,
                )
                .weight(1f)
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            IconComponent(
                painter = painterResource(Res.drawable.ic_chevron_down),
                contentDescription = stringResource(Res.string.subtract),
            )
        }
    }
}
