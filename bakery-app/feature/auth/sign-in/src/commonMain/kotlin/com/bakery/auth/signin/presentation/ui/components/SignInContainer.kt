package com.bakery.auth.signin.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.go_to_sign_up
import com.bakery.resources.generated.resources.ic_chevron_right
import com.bakery.ui.Fonts
import com.bakery.ui.components.buttons.CardButton
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.window.LocalWindowUtils
import com.bakery.ui.window.WindowUtils
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInContainer(modifier: Modifier = Modifier, state: SignInState, onEvent: (SignInEvents) -> Unit) {
    val windowUtils: WindowUtils = LocalWindowUtils.current

    when (windowUtils.isWideLayout()) {
        true -> {
            SignInLandscapeLayout(
                modifier = modifier,
                state = state,
                onEvent = onEvent,
            )
        }

        false -> {
            SignInMainSection(
                modifier = modifier,
                state = state,
                onEvent = onEvent,
                footer = {
                    CardButton(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
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
                },
            )
        }
    }
}
