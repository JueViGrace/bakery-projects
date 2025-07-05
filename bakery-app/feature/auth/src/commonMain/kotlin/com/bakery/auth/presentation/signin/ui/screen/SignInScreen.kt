package com.bakery.auth.presentation.signin.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.auth.presentation.signin.events.SignInEvents
import com.bakery.auth.presentation.signin.state.SignInState
import com.bakery.auth.presentation.signin.ui.components.SignInContainer
import com.bakery.auth.presentation.signin.viewmodel.SignInViewModel
import com.bakery.ui.components.containers.AppContainer
import com.bakery.ui.components.containers.ScaffoldContainer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(
    viewmodel: SignInViewModel = koinViewModel()
) {
    val state: SignInState by viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.submitError) {
        if (state.submitError != null) {
            viewmodel.onEvent(SignInEvents.ClearError)
        }
    }

    SignInContent(
        state = state,
        onEvent = viewmodel::onEvent
    )
}

@Composable
fun SignInContent(
    state: SignInState,
    onEvent: (SignInEvents) -> Unit = {}
) {
    ScaffoldContainer {
        SignInContainer(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onEvent = onEvent
        )
    }
}

@Composable
@Preview
fun SignInPreview() {
    AppContainer {
        SignInContent(
            state = SignInState()
        )
    }
}
