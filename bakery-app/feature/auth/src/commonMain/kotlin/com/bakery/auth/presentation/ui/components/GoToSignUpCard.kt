package com.bakery.auth.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.go_to_sign_up
import com.bakery.resources.generated.resources.ic_chevron_right
import com.bakery.resources.generated.resources.not_have_account
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.RowDisplay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun GoToSignUpCard(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    title: String = stringResource(Res.string.not_have_account),
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        ElevatedCard(
            onClick = onClick,
        ) {
            RowDisplay(
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                trailingContent = {
                    IconComponent(
                        modifier = Modifier
                            .sizeIn(
                                minWidth = 14.dp,
                                maxWidth = 24.dp,
                                minHeight = 14.dp,
                                maxHeight = 24.dp,
                            ),
                        painter = painterResource(Res.drawable.ic_chevron_right),
                        contentDescription = stringResource(Res.string.go_to_sign_up),
                    )
                }
            ) {
                Text(
                    text = title,
                    style = Fonts.smallTextStyle,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
