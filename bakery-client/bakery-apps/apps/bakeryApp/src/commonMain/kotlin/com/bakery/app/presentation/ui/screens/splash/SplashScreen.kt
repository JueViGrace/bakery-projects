package com.bakery.app.presentation.ui.screens.splash

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.logo
import com.bakery.ui.components.display.ImageComponent
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashScreen(painter: Painter) {
    val infiniteTransition: InfiniteTransition = rememberInfiniteTransition()
    val scale: Float by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700),
            repeatMode = RepeatMode.Restart,
        ),
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.scale(scale),
        ) {
            ImageComponent(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(4.dp),
                painter = painter,
                contentDescription = stringResource(Res.string.logo),
            )
        }
    }
}
