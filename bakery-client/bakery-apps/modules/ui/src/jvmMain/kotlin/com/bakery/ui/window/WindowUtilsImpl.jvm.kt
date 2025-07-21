package com.bakery.ui.window

import androidx.compose.runtime.Composable

/*
* JVM platform implementation of the WindowUtils interface
* */
internal actual object WindowUtilsImpl : WindowUtils {
    @Composable
    actual override fun getScreenOrientation(): Orientation =
        if (getScreenWidth() >= getScreenHeight()) Orientation.Landscape else Orientation.Portrait
}
