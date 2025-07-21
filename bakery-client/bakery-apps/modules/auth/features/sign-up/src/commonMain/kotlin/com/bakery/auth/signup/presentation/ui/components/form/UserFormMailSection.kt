package com.bakery.auth.signup.presentation.ui.components.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.bakery.auth.ui.AuthFormItemSection
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.email
import com.bakery.resources.generated.resources.ic_account
import com.bakery.resources.generated.resources.ic_mail
import com.bakery.resources.generated.resources.ic_phone
import com.bakery.resources.generated.resources.phone_number
import com.bakery.resources.generated.resources.required_field
import com.bakery.resources.generated.resources.type_your
import com.bakery.resources.generated.resources.username
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.input.DefaultInputField
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserFormMailSection(
    phoneNumber: String,
    email: String,
    username: String,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    phoneNumberError: String? = null,
    emailError: String? = null,
    usernameError: String? = null,
) {
    val phoneNumberLabel = stringResource(Res.string.phone_number)
    val emailLabel = stringResource(Res.string.email)
    val usernameLabel = stringResource(Res.string.username)
    val requiredField = "* ${stringResource(Res.string.required_field).lowercase()}"

    AuthFormItemSection(
        modifier = modifier,
    ) {
        DefaultInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "${stringResource(Res.string.type_your)} ${phoneNumberLabel.lowercase()}...",
            label = phoneNumberLabel,
            leadingIcon = {
                IconComponent(
                    painter = painterResource(Res.drawable.ic_phone),
                    contentDescription = phoneNumberLabel,
                )
            },
            supportingText = phoneNumberError ?: if (phoneNumber.isEmpty()) {
                requiredField
            } else {
                null
            },
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            isError = phoneNumberError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
        )
        DefaultInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "${stringResource(Res.string.type_your)} ${emailLabel.lowercase()}...",
            label = emailLabel,
            leadingIcon = {
                IconComponent(
                    painter = painterResource(Res.drawable.ic_mail),
                    contentDescription = emailLabel,
                )
            },
            supportingText = emailError ?: if (email.isEmpty()) {
                requiredField
            } else {
                null
            },
            value = email,
            onValueChange = onEmailChange,
            isError = emailError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )
        DefaultInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "${stringResource(Res.string.type_your)} ${usernameLabel.lowercase()}...",
            label = usernameLabel,
            leadingIcon = {
                IconComponent(
                    painter = painterResource(Res.drawable.ic_account),
                    contentDescription = usernameLabel,
                )
            },
            supportingText = usernameError,
            value = username,
            onValueChange = onUsernameChange,
            isError = usernameError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
    }
}
