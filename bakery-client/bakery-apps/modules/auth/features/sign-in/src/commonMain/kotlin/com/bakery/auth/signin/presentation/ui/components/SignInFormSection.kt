package com.bakery.auth.signin.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.auth.ui.AuthFormSection
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.forgot_password
import com.bakery.resources.generated.resources.ic_eye
import com.bakery.resources.generated.resources.ic_eye_off
import com.bakery.resources.generated.resources.ic_lock
import com.bakery.resources.generated.resources.ic_user
import com.bakery.resources.generated.resources.log_in
import com.bakery.resources.generated.resources.password
import com.bakery.resources.generated.resources.type_your
import com.bakery.resources.generated.resources.username
import com.bakery.ui.Fonts
import com.bakery.ui.components.buttons.OutlinedLoadButton
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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
                            BasicText(
                                modifier = Modifier.clickable(
                                    onClick = {
                                        onEvent(SignInEvents.OnForgot(ForgotAction.OnForgotPassword))
                                    },
                                ),
                                text = stringResource(Res.string.forgot_password),
                                style = TextStyle(
                                    textDecoration = TextDecoration.Underline,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                autoSize = TextAutoSize.StepBased(
                                    minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                                    maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                ),
                            )
                        }
                    },
                    value = state.signInForm.username,
                    onValueChange = { newValue ->
                        onEvent(SignInEvents.OnUsernameChanged(newValue))
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
                            enabled = state.signInForm.password.isNotBlank(),
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
                            BasicText(
                                modifier = Modifier.clickable(
                                    onClick = {
                                        onEvent(SignInEvents.OnForgot(ForgotAction.OnForgotPassword))
                                    },
                                ),
                                text = stringResource(Res.string.forgot_password),
                                style = TextStyle(
                                    textDecoration = TextDecoration.Underline,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                autoSize = TextAutoSize.StepBased(
                                    minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                                    maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                ),
                            )
                        }
                    },
                    value = state.signInForm.password,
                    onValueChange = { newValue ->
                        onEvent(SignInEvents.OnPasswordChanged(newValue))
                    },
                    isError = state.formValidation.passwordError != null,
                    visualTransformation = if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    }
                )
            }
        },
        submitContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedLoadButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.log_in),
                    onClick = {
                        onEvent(SignInEvents.OnSubmit)
                    },
                    enabled = state.submitEnabled,
                    isLoading = state.submitLoading,
                    isSuccess = state.submitSuccess,
                    isError = state.submitError != null,
                )

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
