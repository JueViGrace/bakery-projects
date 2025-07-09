package com.bakery.ui.components.input

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.cancel
import com.bakery.resources.generated.resources.date
import com.bakery.resources.generated.resources.ic_calendar_week
import com.bakery.resources.generated.resources.ok
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.IconComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    date: String,
    onDateChange: (dateMillis: Long) -> Unit,
    modifier: Modifier = Modifier,
    dateFormat: String = "dd/mm/aaaa",
    enabled: Boolean = true,
    labelText: String = stringResource(Res.string.date),
    selectableDates: SelectableDates = DatePickerDefaults.AllDates,
    supportingText: String? = null,
    isError: Boolean = supportingText != null,
) {
    val datePickerState: DatePickerState = rememberDatePickerState(
        selectableDates = selectableDates,
    )
    var showDatePicker: Boolean by remember { mutableStateOf(false) }
    val focusManager: FocusManager = LocalFocusManager.current

    LaunchedEffect(showDatePicker) {
        datePickerState.selectedDateMillis?.let { date ->
            onDateChange(date)
        }
    }

    DialogTextField(
        modifier = modifier,
        value = date,
        onClicked = {
            showDatePicker = true
        },
        showDialog = showDatePicker,
        label = {
            Text(
                text = labelText,
                style = Fonts.labelTextStyle,
            )
        },
        placeholder = {
            Text(
                text = dateFormat,
            )
        },
        trailingIcon = {
            IconComponent(
                painter = painterResource(Res.drawable.ic_calendar_week),
            )
        },
        supportingText = supportingText?.let { message ->
            {
                Text(
                    text = message,
                    style = Fonts.labelTextStyle,
                )
            }
        },
        enabled = enabled,
        isError = isError,
    ) {
        DatePickerDialog(
            onDismissRequest = {
                focusManager.clearFocus()
                showDatePicker = false
            },
            confirmButton = {
                ElevatedButton(
                    onClick = {
                        focusManager.clearFocus()
                        showDatePicker = false
                    },
                ) {
                    Text(
                        text = stringResource(Res.string.ok),
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        focusManager.clearFocus()
                        showDatePicker = false
                    },
                ) {
                    Text(
                        text = stringResource(Res.string.cancel),
                    )
                }
            },
        ) {
            DatePicker(
                state = datePickerState,
            )
        }
    }
}
