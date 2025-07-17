package com.bakery.auth.signup.presentation.ui.components.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bakery.auth.ui.AuthFormItemSection
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.birth_date
import com.bakery.resources.generated.resources.first_name
import com.bakery.resources.generated.resources.ic_account
import com.bakery.resources.generated.resources.ic_address_book
import com.bakery.resources.generated.resources.last_name
import com.bakery.resources.generated.resources.required_field
import com.bakery.resources.generated.resources.type_your
import com.bakery.resources.generated.resources.username
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.input.DateField
import com.bakery.ui.components.input.DefaultInputField
import com.bakery.ui.dates.Dates
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFormNamesSection(
    firstName: String,
    lastName: String,
    birthDate: String,
    username: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBirthDateChange: (Long) -> Unit,
    onUsernameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    firstNameError: String? = null,
    lastNameError: String? = null,
    birthDateError: String? = null,
    usernameError: String? = null,
) {
    val firstNameLabel = stringResource(Res.string.first_name)
    val lastNameLabel = stringResource(Res.string.last_name)
    val usernameLabel = stringResource(Res.string.username)
    val requiredField = "* ${stringResource(Res.string.required_field).lowercase()}"

    AuthFormItemSection(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "${stringResource(Res.string.type_your)} ${firstNameLabel.lowercase()}...",
            label = firstNameLabel,
            leadingIcon = {
                IconComponent(
                    painter = painterResource(Res.drawable.ic_address_book),
                    contentDescription = firstNameLabel,
                )
            },
            supportingText = firstNameError ?: if (firstName.isEmpty()) {
                requiredField
            } else {
                null
            },
            value = firstName,
            onValueChange = onFirstNameChange,
            isError = firstNameError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        DefaultInputField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "${stringResource(Res.string.type_your)} ${lastNameLabel.lowercase()}...",
            label = lastNameLabel,
            leadingIcon = {
                IconComponent(
                    painter = painterResource(Res.drawable.ic_address_book),
                    contentDescription = lastNameLabel,
                )
            },
            supportingText = lastNameError ?: if (lastName.isEmpty()) {
                requiredField
            } else {
                null
            },
            value = lastName,
            onValueChange = onLastNameChange,
            isError = lastNameError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        DateField(
            modifier = Modifier.fillMaxWidth(),
            labelText = stringResource(Res.string.birth_date),
            supportingText = birthDateError ?: if (birthDate.isEmpty()) {
                requiredField
            } else {
                null
            },
            date = birthDate,
            onDateChange = onBirthDateChange,
            selectableDates = Dates.selectableDatesBeforeToday,
            isError = birthDateError != null,
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
