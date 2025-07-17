package com.bakery.auth.signup.presentation.ui.components.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.bakery.auth.ui.AuthFormItemSection
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.confirm_password
import com.bakery.resources.generated.resources.ic_eye
import com.bakery.resources.generated.resources.ic_eye_off
import com.bakery.resources.generated.resources.ic_lock
import com.bakery.resources.generated.resources.password
import com.bakery.resources.generated.resources.type_your
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.input.DefaultInputField
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserFormPasswordSection(
    password: String,
    confirmPassword: String,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    passwordError: String? = null,
    confirmPasswordError: String? = null,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var showConfirmPassword by rememberSaveable { mutableStateOf(false) }

    val passwordLabel = stringResource(Res.string.password)
    val confirmPasswordLabel = stringResource(Res.string.confirm_password)

    AuthFormItemSection(
        modifier = modifier,
    ) {
        DefaultInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "${stringResource(Res.string.type_your)} ${passwordLabel.lowercase()}...",
            label = passwordLabel,
            leadingIcon = {
                IconComponent(
                    painter = painterResource(Res.drawable.ic_lock),
                    contentDescription = passwordLabel,
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        showPassword = !showPassword
                    },
                    enabled = password.isNotBlank(),
                ) {
                    IconComponent(
                        painter = painterResource(
                            if (showPassword) Res.drawable.ic_eye_off else Res.drawable.ic_eye
                        ),
                        contentDescription = passwordLabel,
                    )
                }
            },
            supportingText = passwordError,
            value = password,
            onValueChange = onPasswordChange,
            isError = passwordError != null,
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        DefaultInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "${stringResource(Res.string.type_your)} ${confirmPasswordLabel.lowercase()}...",
            label = confirmPasswordLabel,
            leadingIcon = {
                IconComponent(
                    painter = painterResource(Res.drawable.ic_lock),
                    contentDescription = confirmPasswordLabel,
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        showConfirmPassword = !showConfirmPassword
                    },
                    enabled = confirmPassword.isNotBlank(),
                ) {
                    IconComponent(
                        painter = painterResource(
                            if (showConfirmPassword) Res.drawable.ic_eye_off else Res.drawable.ic_eye
                        ),
                        contentDescription = confirmPasswordLabel,
                    )
                }
            },
            supportingText = confirmPasswordError,
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            enabled = password.isNotBlank(),
            isError = confirmPasswordError != null,
            visualTransformation = if (showConfirmPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
    }
}
