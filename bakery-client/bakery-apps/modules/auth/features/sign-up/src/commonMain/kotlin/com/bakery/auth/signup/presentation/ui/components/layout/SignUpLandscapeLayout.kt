package com.bakery.auth.signup.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bakery.auth.signup.presentation.events.SignUpEvents
import com.bakery.auth.signup.presentation.state.SignUpState

@Composable
fun SignUpLandscapeLayout(
    modifier: Modifier = Modifier,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SignUpPortraitLayout(
            modifier = Modifier.weight(1f),
            state = state,
            onEvent = onEvent,
        )
        // todo: design
    }
}
