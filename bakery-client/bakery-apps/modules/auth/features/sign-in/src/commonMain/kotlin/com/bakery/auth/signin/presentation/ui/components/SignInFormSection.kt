package com.bakery.auth.signin.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.auth.ui.AuthFormSection
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.forgot_password
import com.bakery.resources.generated.resources.forgot_username
import com.bakery.resources.generated.resources.ic_eye
import com.bakery.resources.generated.resources.ic_eye_off
import com.bakery.resources.generated.resources.ic_lock
import com.bakery.resources.generated.resources.ic_user
import com.bakery.resources.generated.resources.log_in
import com.bakery.resources.generated.resources.password
import com.bakery.resources.generated.resources.type_your
import com.bakery.resources.generated.resources.username
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.input.DefaultInputField
import com.bakery.ui.navigation.models.ForgotAction
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInFormSection(
    state: SignInState,
    onEvent: (SignInEvents) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    var showPassword by remember { mutableStateOf(false) }
    val username = stringResource(Res.string.username)
    val password = stringResource(Res.string.password)

    AuthFormSection(
        modifier = modifier.padding(contentPadding),
        fields = {
            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${username.lowercase()}...",
                label = username,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_user),
                        contentDescription = username,
                    )
                },
                supportingText = state.formValidation.usernameError?.let { stringResource(it) },
                underlineText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.clickable(
                                onClick = {
                                    onEvent(SignInEvents.OnForgot(ForgotAction.OnForgotUsername))
                                },
                            ),
                            text = stringResource(Res.string.forgot_username),
                            style = Fonts.smallTextStyle,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                },
                value = state.logInForm.username,
                onValueChange = { newValue ->
                    onEvent(SignInEvents.OnEmailChanged(newValue))
                },
                isError = state.formValidation.usernameError != null,
            )

            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${password.lowercase()}...",
                label = password,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_lock),
                        contentDescription = password,
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showPassword = !showPassword
                        },
                        enabled = state.logInForm.password.isNotBlank(),
                    ) {
                        IconComponent(
                            painter = painterResource(
                                if (showPassword) Res.drawable.ic_eye_off else Res.drawable.ic_eye
                            ),
                            contentDescription = password,
                        )
                    }
                },
                supportingText = state.formValidation.passwordError?.let { stringResource(it) },
                underlineText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.clickable(
                                onClick = {
                                    onEvent(SignInEvents.OnForgot(ForgotAction.OnForgotPassword))
                                },
                            ),
                            text = stringResource(Res.string.forgot_password),
                            style = Fonts.smallTextStyle,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                },
                value = state.logInForm.password,
                onValueChange = { newValue ->
                    onEvent(SignInEvents.OnPasswordChanged(newValue))
                },
                isError = state.formValidation.passwordError != null,
            )
        },
        submitContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(SignInEvents.OnSubmit)
                    },
                    enabled = state.submitEnabled && !state.submitLoading,
                ) {
                    if (state.submitLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = stringResource(Res.string.log_in),
                        )
                    }
                }

                state.submitError?.let { error ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = error,
                            style = Fonts.smallTextStyle,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        },
    )
}
