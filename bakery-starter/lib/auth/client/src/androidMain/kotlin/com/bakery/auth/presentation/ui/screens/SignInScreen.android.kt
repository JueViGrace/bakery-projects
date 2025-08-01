package com.bakery.auth.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.auth.presentation.events.SignInEvents
import com.bakery.auth.presentation.ui.components.AuthFooter
import com.bakery.auth.presentation.ui.components.AuthTitle
import com.bakery.auth.presentation.ui.components.HaveAnAccountComponent
import com.bakery.auth.presentation.ui.components.PasswordTextField
import com.bakery.auth.presentation.ui.components.UsernameTextField
import com.bakery.auth.presentation.ui.components.mobile.MobileAuthLayout
import com.bakery.auth.presentation.viewmodel.SignInViewModel
import com.bakery.core.presentation.ui.components.display.TextComponent
import com.bakery.core.resources.resources.generated.resources.Res
import com.bakery.core.resources.resources.generated.resources.dont_have_an_account
import com.bakery.core.resources.resources.generated.resources.forgot_password
import com.bakery.core.resources.resources.generated.resources.log_in
import com.bakery.core.resources.resources.generated.resources.please_log_in
import com.bakery.core.resources.resources.generated.resources.sign_up
import com.bakery.core.resources.resources.generated.resources.welcome_back
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun SignInScreen(viewModel: SignInViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    MobileAuthLayout(
        title = {
            AuthTitle(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(0.3f),
                title = {
                    TextComponent(
                        text = stringResource(Res.string.welcome_back),
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                    )
                    TextComponent(
                        text = stringResource(Res.string.please_log_in),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    )
                },
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth().weight(0.6f),
                verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    UsernameTextField(
                        value = state.username,
                        onValueChange = { newValue ->
                            onEvent(SignInEvents.OnSignInUsernameChanged(newValue))
                        },
                        errorMessage = state.usernameError?.let { stringResource(it) },
                    )

                    PasswordTextField(
                        value = state.password,
                        onValueChange = { newValue ->
                            onEvent(SignInEvents.OnSignInPasswordChanged(newValue))
                        },
                        errorMessage = state.passwordError?.let { stringResource(it) },
                        passwordVisibility = state.passwordVisibility,
                        onVisibilityChange = {
                            onEvent(SignInEvents.TogglePasswordVisibility)
                        },
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextComponent(
                        modifier =
                            Modifier.clickable {
                                onEvent(SignInEvents.OnNavigateToForgotPassword)
                            },
                        text = stringResource(Res.string.forgot_password),
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    onClick = {
                        onEvent(SignInEvents.OnSignInSubmit)
                    },
                    enabled = state.signInEnabled,
                ) {
                    TextComponent(
                        text = stringResource(Res.string.log_in),
                    )
                }

                HaveAnAccountComponent(
                    initialText = "${stringResource(Res.string.dont_have_an_account)}, ",
                    screenText = "${stringResource(Res.string.sign_up)} ->",
                    onClick = {
                        onEvent(SignInEvents.OnNavigateToSignUp)
                    },
                )
            }
        },
        footer = {
            AuthFooter(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(0.1f),
            )
        },
    )
}
