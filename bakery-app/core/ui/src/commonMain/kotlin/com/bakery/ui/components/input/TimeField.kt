package com.bakery.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.cancel
import com.bakery.resources.generated.resources.hours
import com.bakery.resources.generated.resources.ic_clock
import com.bakery.resources.generated.resources.ok
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.IconComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeField(
    time: String,
    onTimeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    labelText: String = stringResource(Res.string.hours),
    supportingText: String? = null,
    isError: Boolean = supportingText != null
) {
    val timePickerState: TimePickerState = rememberTimePickerState()
    var showTimePicker: Boolean by remember { mutableStateOf(false) }
    val focusManager: FocusManager = LocalFocusManager.current

    LaunchedEffect(showTimePicker) {
        onTimeChange(
            "${
                timePickerState.hour.toString().padStart(2, '0')
            }:${
                timePickerState.minute.toString().padStart(2, '0')
            }"
        )
    }

    DialogTextField(
        modifier = modifier,
        showDialog = showTimePicker,
        value = time,
        onClicked = {
            showTimePicker = true
        },
        label = {
            Text(
                text = labelText,
                style = Fonts.labelTextStyle
            )
        },
        placeholder = {
            Text(
                text = "--:--"
            )
        },
        trailingIcon = {
            IconComponent(
                painter = painterResource(Res.drawable.ic_clock)
            )
        },
        supportingText = supportingText?.let { message ->
            {
                Text(
                    text = message,
                    style = Fonts.labelTextStyle
                )
            }
        },
        enabled = enabled,
        isError = isError,
    ) {
        Dialog(
            onDismissRequest = {
                focusManager.clearFocus()
                showTimePicker = false
            }
        ) {
            Box(
                modifier = Modifier.sizeIn(minWidth = 280.dp, maxWidth = 560.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8)
                        )
                        .clip(RoundedCornerShape(8))
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        TimePicker(state = timePickerState)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                focusManager.clearFocus()
                                showTimePicker = false
                            }
                        ) {
                            Text(
                                text = stringResource(Res.string.cancel)
                            )
                        }
                        ElevatedButton(
                            onClick = {
                                focusManager.clearFocus()
                                showTimePicker = false
                            }
                        ) {
                            Text(
                                text = stringResource(Res.string.ok)
                            )
                        }
                    }
                }
            }
        }
    }
}
