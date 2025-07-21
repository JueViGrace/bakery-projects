package com.bakery.ui.components.layout.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bakery.ui.components.containers.ScaffoldType
import com.bakery.ui.components.observable.ObserveAsEvents
import com.bakery.ui.messages.LocalMessages
import com.bakery.ui.messages.Messages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

@Composable
internal fun ScaffoldContent(
    scaffold: ScaffoldType,
    content: @Composable () -> Unit,
) {
    val messages: Messages = LocalMessages.current
    val scope: CoroutineScope = rememberCoroutineScope()
    val hostState: SnackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(
        flow = messages.messages,
    ) { msg ->
        scope.launch {
            val message: String = if (msg.description != null) {
                "${getString(msg.message)}. ${msg.description}"
            } else {
                getString(msg.message)
            }
            hostState.showSnackbar(message = message)
        }
    }

    scaffold(
        ScaffoldDefaults.contentWindowInsets,
        { SnackbarHost(hostState = hostState) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(WindowInsets.navigationBars),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
