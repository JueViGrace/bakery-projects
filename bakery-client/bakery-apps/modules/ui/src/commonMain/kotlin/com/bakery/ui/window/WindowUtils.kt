package com.bakery.ui.window

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
* Provides the screen size and orientation and any other related information if needed
* */
interface WindowUtils {
    @Composable
    fun getScreenWidth(): Dp {
        val windowInfo: WindowInfo = LocalWindowInfo.current
        val density: Density = LocalDensity.current
        return with(density) {
            windowInfo.containerSize.width.toDp()
        }
    }

    @Composable
    fun getScreenHeight(): Dp {
        val windowInfo: WindowInfo = LocalWindowInfo.current
        val density: Density = LocalDensity.current
        return with(density) {
            windowInfo.containerSize.height.toDp()
        }
    }

    @Composable
    fun getScreenOrientation(): Orientation

    @Composable
    fun getScreenSize(): ScreenSize {
        val width: Dp = getScreenWidth()
        val height: Dp = getScreenHeight()

        return when (getScreenOrientation()) {
            Orientation.Portrait -> {
                when {
                    width < 600.dp ||
                        (height >= 480.dp && height < 900.dp) -> ScreenSize.Compact

                    (width >= 600.dp && width < 840.dp) ||
                        (height >= 900.dp) -> ScreenSize.Medium

                    width >= 840.dp || height > 900.dp -> ScreenSize.Large
                    else -> ScreenSize.Large
                }
            }

            Orientation.Landscape -> {
                when {
                    height < 480.dp || width <= 840.dp -> ScreenSize.Compact
                    (height >= 480.dp && height < 900.dp) ||
                        width > 840.dp -> ScreenSize.Medium

                    height >= 1200.dp || width > 840.dp -> ScreenSize.Large
                    else -> ScreenSize.Large
                }
            }
        }
    }

    @Composable
    fun isPortrait(): Boolean = getScreenOrientation() == Orientation.Portrait

    @Composable
    fun isLandscape(): Boolean = getScreenOrientation() == Orientation.Landscape

    companion object {
        val instance: WindowUtils = WindowUtilsImpl
    }
}
