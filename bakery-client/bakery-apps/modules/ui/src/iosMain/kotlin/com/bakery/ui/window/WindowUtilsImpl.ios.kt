package com.bakery.ui.window

import androidx.compose.runtime.Composable

/*
* Ios platform implementation of the WindowUtils interface
* */
internal actual object WindowUtilsImpl : WindowUtils {
    @Composable
    actual override fun getScreenOrientation(): Orientation = Orientation.Portrait
}
