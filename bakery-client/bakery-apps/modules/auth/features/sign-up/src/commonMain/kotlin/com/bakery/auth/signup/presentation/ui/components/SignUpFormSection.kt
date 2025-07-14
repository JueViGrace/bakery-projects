package com.bakery.auth.signup.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bakery.auth.signup.presentation.events.SignUpEvents
import com.bakery.auth.signup.presentation.state.SignUpState
import com.bakery.auth.ui.AuthFormSection
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.accept_privacy_policy
import com.bakery.resources.generated.resources.accept_terms_and_conditions
import com.bakery.resources.generated.resources.confirm_password
import com.bakery.resources.generated.resources.display_name
import com.bakery.resources.generated.resources.email
import com.bakery.resources.generated.resources.first_name
import com.bakery.resources.generated.resources.ic_account
import com.bakery.resources.generated.resources.ic_address_book
import com.bakery.resources.generated.resources.ic_eye
import com.bakery.resources.generated.resources.ic_eye_off
import com.bakery.resources.generated.resources.ic_lock
import com.bakery.resources.generated.resources.ic_mail
import com.bakery.resources.generated.resources.ic_phone
import com.bakery.resources.generated.resources.ic_user
import com.bakery.resources.generated.resources.last_name
import com.bakery.resources.generated.resources.password
import com.bakery.resources.generated.resources.phone_number
import com.bakery.resources.generated.resources.submit
import com.bakery.resources.generated.resources.type_your
import com.bakery.resources.generated.resources.username
import com.bakery.ui.Fonts
import com.bakery.ui.components.check.CheckBoxRow
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.TextComponent
import com.bakery.ui.components.input.DefaultInputField
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpFormSection(
    modifier: Modifier = Modifier,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val firstName = stringResource(Res.string.first_name)
    val lastName = stringResource(Res.string.last_name)
    val username = stringResource(Res.string.username)
    val alias = stringResource(Res.string.display_name)
    val email = stringResource(Res.string.email)
    val password = stringResource(Res.string.password)
    val confirmPassword = stringResource(Res.string.confirm_password)
    val phoneNumber = stringResource(Res.string.phone_number)

    AuthFormSection(
        modifier = modifier,
        fields = {
            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${firstName.lowercase()}...",
                label = firstName,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_address_book),
                        contentDescription = firstName,
                    )
                },
                supportingText = state.formValidation.firstNameError?.let { stringResource(it) },
                value = state.signUpForm.firstName,
                onValueChange = { newValue ->
                    onEvent(SignUpEvents.OnFirstNameChanged(newValue))
                },
                isError = state.formValidation.firstNameError != null,
            )

            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${lastName.lowercase()}...",
                label = lastName,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_address_book),
                        contentDescription = lastName,
                    )
                },
                supportingText = state.formValidation.lastNameError?.let { stringResource(it) },
                value = state.signUpForm.lastName,
                onValueChange = { newValue ->
                    onEvent(SignUpEvents.OnLastNameChanged(newValue))
                },
                isError = state.formValidation.lastNameError != null,
            )

            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${username.lowercase()}...",
                label = username,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_account),
                        contentDescription = username,
                    )
                },
                supportingText = state.formValidation.usernameError?.let { stringResource(it) },
                value = state.signUpForm.username,
                onValueChange = { newValue ->
                    onEvent(SignUpEvents.OnUsernameChanged(newValue))
                },
                isError = state.formValidation.usernameError != null,
            )

            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${email.lowercase()}...",
                label = email,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_mail),
                        contentDescription = email,
                    )
                },
                supportingText = state.formValidation.emailError?.let { stringResource(it) },
                value = state.signUpForm.email,
                onValueChange = { newValue ->
                    onEvent(SignUpEvents.OnEmailChanged(newValue))
                },
                isError = state.formValidation.emailError != null,
            )

            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${phoneNumber.lowercase()}...",
                label = phoneNumber,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_phone),
                        contentDescription = phoneNumber,
                    )
                },
                supportingText = state.formValidation.phoneNumberError?.let { stringResource(it) },
                value = state.signUpForm.phoneNumber,
                onValueChange = { newValue ->
                    onEvent(SignUpEvents.OnPhoneChanged(newValue))
                },
                isError = state.formValidation.phoneNumberError != null,
            )

            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${alias.lowercase()}...",
                label = alias,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_user),
                        contentDescription = alias,
                    )
                },
                supportingText = state.formValidation.aliasError?.let { stringResource(it) },
                value = state.signUpForm.alias.ifEmpty { state.signUpForm.username },
                onValueChange = { newValue ->
                    onEvent(SignUpEvents.OnAliasChanged(newValue))
                },
                isError = state.formValidation.aliasError != null,
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
                        enabled = state.signUpForm.password.isNotBlank(),
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
                value = state.signUpForm.password,
                onValueChange = { newValue ->
                    onEvent(SignUpEvents.OnPasswordChanged(newValue))
                },
                isError = state.formValidation.passwordError != null,
            )

            DefaultInputField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "${stringResource(Res.string.type_your)} ${confirmPassword.lowercase()}...",
                label = confirmPassword,
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_lock),
                        contentDescription = confirmPassword,
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showPassword = !showPassword
                        },
                        enabled = state.signUpForm.confirmPassword.isNotBlank(),
                    ) {
                        IconComponent(
                            painter = painterResource(
                                if (showPassword) Res.drawable.ic_eye_off else Res.drawable.ic_eye
                            ),
                            contentDescription = confirmPassword,
                        )
                    }
                },
                supportingText = state.formValidation.confirmPasswordError?.let { stringResource(it) },
                value = state.signUpForm.confirmPassword,
                onValueChange = { newValue ->
                    onEvent(SignUpEvents.OnConfirmPasswordChanged(newValue))
                },
                enabled = state.signUpForm.password.isNotBlank(),
                isError = state.formValidation.confirmPasswordError != null,
            )

            CheckBoxRow(
                modifier = Modifier.fillMaxWidth(),
                checked = state.signUpForm.termsAndConditions,
                onCheckedChange = {
                    onEvent(SignUpEvents.ToggleTerms)
                },
                label = {
                    TextComponent(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    onEvent(SignUpEvents.OnTerms)
                                }
                            ),
                        text = stringResource(Res.string.accept_terms_and_conditions),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        ),
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                            maxFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        )
                    )
                },
                supportingText = state.formValidation.termsAndConditionsError?.let { error ->
                    {
                        TextComponent(
                            text = stringResource(error),
                            color = MaterialTheme.colorScheme.error,
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                                maxFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            )
                        )
                    }
                }
            )

            CheckBoxRow(
                modifier = Modifier.fillMaxWidth(),
                checked = state.signUpForm.privacyPolicy,
                onCheckedChange = {
                    onEvent(SignUpEvents.TogglePrivacyPolicy)
                },
                label = {
                    TextComponent(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    onEvent(SignUpEvents.OnPrivacyPolicy)
                                }
                            ),
                        text = stringResource(Res.string.accept_privacy_policy),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        ),
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                            maxFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        )
                    )
                },
                supportingText = state.formValidation.privacyPolicyError?.let { error ->
                    {
                        TextComponent(
                            text = stringResource(error),
                            color = MaterialTheme.colorScheme.error,
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                                maxFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            )
                        )
                    }
                }
            )
        },
        submitContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(SignUpEvents.OnSubmit)
                    },
                    enabled = state.submitEnabled && !state.submitLoading,
                ) {
                    if (state.submitLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = stringResource(Res.string.submit),
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
