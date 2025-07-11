package com.bakery.ui.components.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bakery.ui.Fonts

@Composable
fun InputFieldWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorText: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholder: String? = null,
    label: String? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            style = Fonts.smallTextStyle,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = !enabled,
            singleLine = true,
            isError = isError,
            prefix = prefix,
            suffix = suffix,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            label = label?.let { label ->
                {
                    Text(
                        text = label,
                        style = Fonts.smallTextStyle,
                    )
                }
            },
            supportingText = errorText?.let { error ->
                {
                    Text(
                        text = error,
                        style = Fonts.labelTextStyle,
                    )
                }
            },
            placeholder = placeholder?.let { placeholder ->
                {
                    Text(
                        text = placeholder,
                        style = Fonts.smallTextStyle,
                    )
                }
            },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors,

        )
    }
}
