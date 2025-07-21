package com.bakery.profile.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.profile.presentation.events.ProfileEvents
import com.bakery.profile.presentation.state.ProfileState
import com.bakery.profile.presentation.viewmodel.ProfileViewModel
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.log_out
import com.bakery.ui.components.containers.ScreenContainer
import com.bakery.ui.components.display.TextComponent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state: ProfileState by viewModel.state.collectAsStateWithLifecycle()

    ProfileContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun ProfileContent(
    state: ProfileState,
    onEvent: (ProfileEvents) -> Unit,
) {
    ScreenContainer {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(ProfileEvents.OnLogOut) }
                ) {
                    TextComponent(
                        text = stringResource(Res.string.log_out),
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            maxFontSize = MaterialTheme.typography.titleLarge.fontSize,
                        ),
                    )
                }
            }
        }
    }
}
