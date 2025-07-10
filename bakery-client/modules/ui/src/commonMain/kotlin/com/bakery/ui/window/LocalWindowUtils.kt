package com.bakery.ui.window

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

/*
* Composition local to provide WindowUtils
* */
val LocalWindowUtils: ProvidableCompositionLocal<WindowUtils> = compositionLocalOf {
    error("No WindowUtils provided")
}
