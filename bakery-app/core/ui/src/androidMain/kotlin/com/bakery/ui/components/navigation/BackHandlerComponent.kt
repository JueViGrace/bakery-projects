package com.bakery.ui.components.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import com.bakery.ui.navigation.navigator.Navigator

@Composable
fun BackHandlerComponent(navigator: Navigator, callBack: (() -> Unit)? = null) {
    val scope: CoroutineScope = rememberCoroutineScope()
    BackHandler {
        if (callBack != null) {
            callBack()
        } else {
            scope.launch(Dispatchers.Main.immediate) {
                awaitFrame()
                navigator.navigateUp()
            }
        }
    }
}
