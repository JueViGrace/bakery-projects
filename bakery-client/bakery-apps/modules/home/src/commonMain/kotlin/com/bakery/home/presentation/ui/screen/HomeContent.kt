package com.bakery.home.presentation.ui.screen

import androidx.compose.runtime.Composable
import com.bakery.home.presentation.events.HomeEvents
import com.bakery.home.presentation.state.HomeState
import com.bakery.home.presentation.ui.components.layout.HomeContentScaffold
import com.bakery.ui.components.containers.ScreenContainer

@Composable
fun HomeContent(
    state: HomeState,
    onEvent: (HomeEvents) -> Unit = {},
) {
    ScreenContainer(
        scaffold = { contentWindowInsets, _, content ->
            HomeContentScaffold(
                state = state,
                onEvent = onEvent,
                contentWindowInsets = contentWindowInsets,
                content = content,
            )
        },
    ) {
    }
}
