package com.bakery.ui.components.input

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DialogTextField(
    value: String,
    onClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    showDialog: Boolean = false,
    enabled: Boolean = true,
    isError: Boolean = false,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    dialogContent: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = showDialog,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            dialogContent()
        }
        ClickableTextField(
            modifier = modifier,
            value = value,
            onClicked = onClicked,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            supportingText = supportingText,
            enabled = enabled,
            isError = isError,
        )
    }
}
