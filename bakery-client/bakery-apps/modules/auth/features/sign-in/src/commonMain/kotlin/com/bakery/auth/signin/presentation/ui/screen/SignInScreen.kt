package com.bakery.auth.signin.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.auth.signin.presentation.ui.components.layout.SignInLandscapeLayout
import com.bakery.auth.signin.presentation.ui.components.layout.SignInPortraitLayout
import com.bakery.auth.signin.presentation.viewmodel.SignInViewModel
import com.bakery.ui.components.containers.AppContainer
import com.bakery.ui.components.containers.ScaffoldContainer
import com.bakery.ui.components.containers.ScreenContainer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(viewmodel: SignInViewModel = koinViewModel()) {
    val state: SignInState by viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.submitError) {
        if (state.submitError != null) {
            viewmodel.onEvent(SignInEvents.ClearError)
        }
    }

    SignInContent(
        state = state,
        onEvent = viewmodel::onEvent,
    )
}

@Composable
fun SignInContent(
    state: SignInState,
    onEvent: (SignInEvents) -> Unit = {},
) {
    ScaffoldContainer {
        ScreenContainer(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        ),
                    ),
                ),
            color = Color.Transparent,
            landscapeLayout = {
                SignInLandscapeLayout(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onEvent = onEvent,
                )
            },
            portraitLayout = {
                SignInPortraitLayout(
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
fun SignInPreview() {
    AppContainer {
        SignInContent(
            state = SignInState(),
        )
    }
}
