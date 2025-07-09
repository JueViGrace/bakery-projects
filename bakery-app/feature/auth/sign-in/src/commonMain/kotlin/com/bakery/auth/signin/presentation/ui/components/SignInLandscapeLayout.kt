package com.bakery.auth.signin.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.go_to_sign_up
import com.bakery.resources.generated.resources.ic_chevron_right
import com.bakery.resources.generated.resources.new_here
import com.bakery.ui.Fonts
import com.bakery.ui.components.buttons.CardButton
import com.bakery.ui.components.display.IconComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInLandscapeLayout(modifier: Modifier = Modifier, state: SignInState, onEvent: (SignInEvents) -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SignInMainSection(
            modifier = Modifier.weight(1f),
            state = state,
            onEvent = onEvent,
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.tertiaryContainer,
                            ),
                            start = Offset(
                                0f,
                                Float.POSITIVE_INFINITY,
                            ),
                            end = Offset(
                                Float.POSITIVE_INFINITY,
                                0f,
                            ),
                        ),
                    ),
                color = Color.Transparent,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(Res.string.new_here),
                        style = Fonts.extraLargeTextStyle,
                    )

                    CardButton(
                        onClick = {
                            onEvent(SignInEvents.OnSignUp)
                        },
                        trailingIcon = {
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
                        },
                        content = {
                            Text(
                                text = stringResource(Res.string.go_to_sign_up),
                                style = Fonts.smallTextStyle,
                                textAlign = TextAlign.Center,
                            )
                        },
                    )
                }
            }
        }
    }
}
