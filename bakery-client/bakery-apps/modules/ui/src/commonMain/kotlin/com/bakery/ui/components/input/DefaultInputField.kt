package com.bakery.ui.components.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bakery.ui.components.display.TextComponent

@Composable
fun DefaultInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholder: String? = null,
    label: String? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    underlineText: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else 10,
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = !enabled,
            singleLine = singleLine,
            isError = isError,
            prefix = prefix,
            suffix = suffix,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            label = label?.let { label ->
                {
                    TextComponent(
                        text = label,
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                            maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        )
                    )
                }
            },
            supportingText = supportingText?.let { error ->
                {
                    TextComponent(
                        text = error,
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                            maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        )
                    )
                }
            },
            placeholder = placeholder?.let { placeholder ->
                {
                    TextComponent(
                        text = placeholder,
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                            maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        )
                    )
                }
            },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors,
            maxLines = maxLines,
        )
        underlineText?.invoke()
    }
}
