package com.bakery.ui.window

import androidx.compose.runtime.Composable

/*
* Implementation of the WindowUtils interface
* */
internal expect object WindowUtilsImpl : WindowUtils {
    @Composable
    override fun getScreenOrientation(): Orientation
}
