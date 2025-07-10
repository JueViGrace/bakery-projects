package com.bakery.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.ui.window.LocalWindowUtils
import com.bakery.ui.window.ScreenSize
import com.bakery.ui.window.WindowUtils

@Composable
fun Modifier.setMaxWidth(maxWidth: Int): Modifier {
    val windowUtils: WindowUtils = LocalWindowUtils.current
    return if (windowUtils.getScreenSize() == ScreenSize.Compact && windowUtils.isPortrait()) {
        Modifier.fillMaxWidth()
    } else {
        Modifier.width(maxWidth.dp)
    }
}
