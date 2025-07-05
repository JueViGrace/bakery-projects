package com.bakery.ui.components.menus

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.option
import com.bakery.resources.generated.resources.select
import com.bakery.ui.components.input.ClickableTextField
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun<T> DropDownMenuField(
    value: String,
    options: List<T>,
    modifier: Modifier = Modifier,
    labelText: String = stringResource(Res.string.select),
    placeHolderText: String = stringResource(Res.string.option).lowercase(),
    supportingText: String? = null,
    isError: Boolean = false,
    itemContent: @Composable (T) -> Unit,
) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { newValue ->
            expanded = newValue
        },
    ) {
        ClickableTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = value,
            onClicked = {
                expanded = true
            },
            label = {
                Text(
                    text = labelText,
                )
            },
            placeholder = {
                Text(
                    text = placeHolderText,
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            supportingText = supportingText?.let { text ->
                {
                    Text(
                        text = text,
                    )
                }
            },
            isError = isError,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                itemContent(option)
            }
        }
    }
}
