package com.bakery.auth.presentation.signin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.presentation.signin.events.SignInEvents
import com.bakery.auth.presentation.signin.state.SignInState
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.compose_multiplatform
import com.bakery.resources.generated.resources.logo
import com.bakery.ui.components.display.ImageComponent
import com.bakery.ui.window.LocalWindowUtils
import com.bakery.ui.window.ScreenSize
import com.bakery.ui.window.WindowUtils
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInMainSection(
    modifier: Modifier = Modifier,
    state: SignInState,
    onEvent: (SignInEvents) -> Unit,
    footer: (@Composable () -> Unit)? = null,
) {
    val windowUtils: WindowUtils = LocalWindowUtils.current

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    val sizeModifier = when (windowUtils.getScreenSize()) {
                        ScreenSize.Compact -> {
                            if (windowUtils.isPortrait()) {
                                Modifier.sizeIn(
                                    minWidth = 100.dp,
                                    minHeight = 100.dp,
                                    maxWidth = 200.dp,
                                    maxHeight = 200.dp
                                )
                            } else {
                                Modifier.size(200.dp)
                            }
                        }

                        ScreenSize.Medium -> {
                            if (windowUtils.isPortrait()) {
                                Modifier.fillMaxWidth(0.4f)
                            } else {
                                Modifier.size(250.dp)
                            }
                        }

                        ScreenSize.Large -> {
                            if (windowUtils.isPortrait()) {
                                Modifier.fillMaxWidth(0.2f)
                            } else {
                                Modifier.size(300.dp)
                            }
                        }
                    }
                    ImageComponent(
                        modifier = Modifier.padding(24.dp).then(sizeModifier),
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = stringResource(Res.string.logo),
                    )
                }

                LogInFormSection(
                    state = state,
                    onEvent = onEvent
                )
            }
        }

        footer?.let {
            item {
                it()
            }
        }
    }
}
