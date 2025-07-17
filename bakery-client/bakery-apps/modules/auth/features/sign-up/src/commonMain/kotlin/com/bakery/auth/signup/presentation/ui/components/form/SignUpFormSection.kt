package com.bakery.auth.signup.presentation.ui.components.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.signup.presentation.events.SignUpEvents
import com.bakery.auth.signup.presentation.state.SignUpState
import com.bakery.auth.ui.AuthFormSection
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.submit
import com.bakery.ui.Fonts
import com.bakery.ui.components.buttons.OutlinedLoadButton
import org.jetbrains.compose.resources.stringResource

// todo: show dot for required fields, change how fields are displayed for better interactivity
@Composable
fun SignUpFormSection(
    modifier: Modifier = Modifier,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit,
) {
    AuthFormSection(
        modifier = modifier,
        pageCount = 3,
        nextEnabled = state.nextEnabled,
        fields = { index ->
            LaunchedEffect(index) {
                onEvent(SignUpEvents.OnIndexChanged(index))
            }

            when (index) {
                0 -> {
                    UserFormNamesSection(
                        firstName = state.signUpForm.firstName,
                        onFirstNameChange = { newValue ->
                            onEvent(SignUpEvents.OnFirstNameChanged(newValue))
                        },
                        lastName = state.signUpForm.lastName,
                        onLastNameChange = { newValue ->
                            onEvent(SignUpEvents.OnLastNameChanged(newValue))
                        },
                        birthDate = state.signUpForm.birthDate,
                        onBirthDateChange = { newValue ->
                            onEvent(SignUpEvents.OnBirthDateChanged(newValue))
                        },
                        username = state.signUpForm.username,
                        onUsernameChange = { newValue ->
                            onEvent(SignUpEvents.OnUsernameChanged(newValue))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        firstNameError = state.formValidation.firstNameError?.let { stringResource(it) },
                        lastNameError = state.formValidation.lastNameError?.let { stringResource(it) },
                        birthDateError = state.formValidation.birthDateError?.let { stringResource(it) },
                        usernameError = state.formValidation.usernameError?.let { stringResource(it) },
                    )
                }
                1 -> {
                    UserFormMailSection(
                        phoneNumber = state.signUpForm.phoneNumber,
                        onPhoneNumberChange = { newValue ->
                            onEvent(SignUpEvents.OnPhoneChanged(newValue))
                        },
                        email = state.signUpForm.email,
                        onEmailChange = { newValue ->
                            onEvent(SignUpEvents.OnEmailChanged(newValue))
                        },
                        address = state.signUpForm.address,
                        onAddressChange = { newValue ->
                            onEvent(SignUpEvents.OnAddressChanged(newValue))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        phoneNumberError = state.formValidation.phoneNumberError?.let { stringResource(it) },
                        emailError = state.formValidation.emailError?.let { stringResource(it) },
                        addressError = state.formValidation.addressError?.let { stringResource(it) },
                    )
                }
                2 -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        UserFormPasswordSection(
                            password = state.signUpForm.password,
                            onPasswordChange = { newValue ->
                                onEvent(SignUpEvents.OnPasswordChanged(newValue))
                            },
                            confirmPassword = state.signUpForm.confirmPassword,
                            onConfirmPasswordChange = { newValue ->
                                onEvent(SignUpEvents.OnConfirmPasswordChanged(newValue))
                            },
                            modifier = Modifier.fillMaxWidth(),
                            passwordError = state.formValidation.passwordError?.let { stringResource(it) },
                            confirmPasswordError = state.formValidation.confirmPasswordError?.let {
                                stringResource(it)
                            },
                        )

                        UserFormPoliciesSection(
                            modifier = Modifier.fillMaxWidth(),
                            termsAndConditions = state.signUpForm.termsAndConditions,
                            onTermsAndConditionsChange = {
                                onEvent(SignUpEvents.ToggleTerms)
                            },
                            privacyPolicy = state.signUpForm.privacyPolicy,
                            onPrivacyPolicyChange = {
                                onEvent(SignUpEvents.TogglePrivacyPolicy)
                            },
                            onTerms = {
                                onEvent(SignUpEvents.OnTerms)
                            },
                            onPrivacyPolicy = {
                                onEvent(SignUpEvents.TogglePrivacyPolicy)
                            },
                            termsAndConditionsError = state.formValidation.termsAndConditionsError?.let {
                                stringResource(it)
                            },
                            privacyPolicyError = state.formValidation.privacyPolicyError?.let { stringResource(it) },
                        )
                    }
                }
            }
        },
        submitContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedLoadButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.submit),
                    onClick = {
                        onEvent(SignUpEvents.OnSubmit)
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
