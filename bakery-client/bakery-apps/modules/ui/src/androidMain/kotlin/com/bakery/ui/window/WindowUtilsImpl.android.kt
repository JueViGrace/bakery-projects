package com.bakery.ui.window

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalConfiguration

/*
* Android platform implementation of the WindowUtils interface
* */
internal actual object WindowUtilsImpl : WindowUtils {
    @Composable
    actual override fun getScreenOrientation(): Orientation {
        var orientation by rememberSaveable { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
        val configuration = LocalConfiguration.current

        LaunchedEffect(configuration) {
            snapshotFlow { configuration.orientation }
                .collect { orientation = it }
        }

        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) Orientation.Landscape else Orientation.Portrait
    }
}
