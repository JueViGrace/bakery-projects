package com.bakery.auth.signin.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.auth.signin.presentation.ui.components.layout.SignInLayout
import com.bakery.auth.signin.presentation.viewmodel.SignInViewModel
import com.bakery.auth.ui.AuthScreenContainer
import com.bakery.ui.components.containers.AppContainer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel()
) {
    val state: SignInState by viewModel.state.collectAsStateWithLifecycle()

    SignInContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun SignInContent(
    state: SignInState,
    onEvent: (SignInEvents) -> Unit = {},
) {
    AuthScreenContainer {
        SignInLayout(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onEvent = onEvent,
        )
    }
}

@Composable
@Preview
fun SignInPreview() {
    AppContainer {
        SignInContent(
            state = SignInState(),
        )
    }
}
