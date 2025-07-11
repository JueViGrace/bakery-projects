package com.bakery.auth.signin.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState

@Composable
fun SignInLandscapeLayout(
    modifier: Modifier = Modifier,
    state: SignInState,
    onEvent: (SignInEvents) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SignInPortraitLayout(
            modifier = Modifier.weight(1f),
            state = state,
            onEvent = onEvent,
        )
        // todo: design
    }
}
