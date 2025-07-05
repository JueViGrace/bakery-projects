package com.bakery.auth.presentation.signin.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.presentation.signin.events.SignInEvents
import com.bakery.auth.presentation.signin.state.SignInState
import com.bakery.auth.presentation.ui.components.GoToSignUpCard
import com.bakery.ui.window.LocalWindowUtils
import com.bakery.ui.window.WindowUtils

@Composable
fun SignInContainer(
    modifier: Modifier = Modifier,
    state: SignInState,
    onEvent: (SignInEvents) -> Unit
) {
    val windowUtils: WindowUtils = LocalWindowUtils.current

    when (windowUtils.isWideLayout()) {
        true -> {
            SignInLandscapeLayout(
                modifier = modifier,
                state = state,
                onEvent = onEvent
            )
        }

        false -> {
            SignInMainSection(
                modifier = modifier,
                state = state,
                onEvent = onEvent,
                footer = {
                    GoToSignUpCard(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                        onClick = {
                            onEvent(SignInEvents.OnSignUp)
                        }
                    )
                }
            )
        }
    }
}
