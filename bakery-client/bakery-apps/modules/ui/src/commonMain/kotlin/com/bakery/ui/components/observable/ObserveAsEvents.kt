package com.bakery.ui.components.observable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, key1: Any? = null, key2: Any? = null, onEvent: (T) -> Unit) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}
