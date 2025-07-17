package com.bakery.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.error
import com.bakery.resources.generated.resources.ic_circle_check
import com.bakery.resources.generated.resources.ic_circle_x
import com.bakery.resources.generated.resources.success
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.TextComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OutlinedLoadButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    isSuccess: Boolean = false,
    isError: Boolean = false,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (isError) {
                MaterialTheme.colorScheme.error
            } else {
                ButtonDefaults.outlinedButtonColors().contentColor
            },
        )
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                )
            }
            isError -> {
                IconComponent(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(Res.drawable.ic_circle_x),
                    contentDescription = stringResource(Res.string.error),
                )
            }
            isSuccess -> {
                IconComponent(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(Res.drawable.ic_circle_check),
                    contentDescription = stringResource(Res.string.success),
                )
            }
            else -> {
                TextComponent(
                    text = text,
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = MaterialTheme.typography.bodySmall.fontSize,
                        maxFontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                )
            }
        }
    }
}
