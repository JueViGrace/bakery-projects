package com.bakery.auth.presentation.signin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bakery.auth.presentation.signin.events.SignInEvents
import com.bakery.auth.presentation.signin.state.SignInState
import com.bakery.auth.presentation.ui.components.GoToSignUpCard
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.go_to_sign_up
import com.bakery.resources.generated.resources.new_here
import com.bakery.ui.Fonts
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInLandscapeLayout(
    modifier: Modifier = Modifier,
    state: SignInState,
    onEvent: (SignInEvents) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SignInMainSection(
            modifier = Modifier.weight(1f),
            state = state,
            onEvent = onEvent
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
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
                                Float.POSITIVE_INFINITY
                            ),
                            end = Offset(
                                Float.POSITIVE_INFINITY,
                                0f
                            )
                        )
                    ),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.new_here),
                        style = Fonts.extraLargeTextStyle
                    )

                    GoToSignUpCard(
                        title = stringResource(Res.string.go_to_sign_up)
                    ) {
                        onEvent(SignInEvents.OnSignUp)
                    }
                }
            }
        }
    }
}
