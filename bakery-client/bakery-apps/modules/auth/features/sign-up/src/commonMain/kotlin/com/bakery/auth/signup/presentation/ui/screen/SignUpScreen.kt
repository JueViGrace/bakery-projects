package com.bakery.auth.signup.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.auth.signup.presentation.events.SignUpEvents
import com.bakery.auth.signup.presentation.state.SignUpState
import com.bakery.auth.signup.presentation.ui.components.layout.SignUpLandscapeLayout
import com.bakery.auth.signup.presentation.ui.components.layout.SignUpPortraitLayout
import com.bakery.auth.signup.presentation.viewmodel.SignUpViewModel
import com.bakery.auth.ui.AuthScreenContainer
import com.bakery.ui.components.containers.AppContainer
import com.bakery.ui.components.containers.ScaffoldContainer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel()
) {
    val state: SignUpState by viewModel.state.collectAsStateWithLifecycle()
    SignUpContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun SignUpContent(
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit = {},
) {
    ScaffoldContainer {
        AuthScreenContainer(
            landscapeLayout = {
                SignUpLandscapeLayout(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onEvent = onEvent,
                )
            },
            portraitLayout = {
                SignUpPortraitLayout(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onEvent = onEvent,
                )
            }
        )
    }
}

@Composable
@Preview
fun SignUpScreenPreview() {
    AppContainer {
        SignUpContent(
            state = SignUpState(),
        )
    }
}
