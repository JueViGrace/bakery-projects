package com.bakery.auth.signin.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.auth.signin.presentation.ui.components.SignInMainSection

@Composable
fun SignInPortraitLayout(
    state: SignInState,
    onEvent: (SignInEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            SignInMainSection(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp,vertical = 8.dp),
                state = state,
                onEvent = onEvent,
            )
        }
    }
}
