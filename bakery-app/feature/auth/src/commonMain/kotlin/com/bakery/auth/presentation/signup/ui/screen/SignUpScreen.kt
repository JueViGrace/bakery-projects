package com.bakery.auth.presentation.signup.ui.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.auth.presentation.signup.state.SignUpState
import com.bakery.auth.presentation.signup.viewmodel.SignUpViewModel
import com.bakery.ui.components.containers.AppContainer
import com.bakery.ui.components.containers.ScaffoldContainer
import com.bakery.ui.navigation.navigator.LocalNavigator
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel()
) {
    val state: SignUpState by viewModel.state.collectAsStateWithLifecycle()
    SignUpContent(
        state = state
    )
}

@Composable
fun SignUpContent(
    state: SignUpState,
) {
    val navigator = LocalNavigator.current
    val scope = rememberCoroutineScope()
    ScaffoldContainer {
        Button(
            onClick = {
                scope.launch {
                    navigator.navigateUp()
                }
            }
        ) {
            Text(
                text = "Go back"
            )
        }
    }
}

@Composable
@Preview
fun SignUpScreenPreview() {
    AppContainer {
        SignUpContent(
            state = SignUpState()
        )
    }
}
