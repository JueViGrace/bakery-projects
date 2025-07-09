package com.bakery.ui.components.buttons

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.go_back
import com.bakery.resources.generated.resources.ic_arrow_left
import com.bakery.ui.components.display.IconComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BackButton(onBack: () -> Unit) {
    IconButton(
        onClick = onBack,
    ) {
        IconComponent(
            painter = painterResource(Res.drawable.ic_arrow_left),
            contentDescription = stringResource(Res.string.go_back),
        )
    }
}
