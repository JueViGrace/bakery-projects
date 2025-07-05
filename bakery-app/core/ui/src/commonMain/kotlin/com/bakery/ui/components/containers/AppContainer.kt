package com.bakery.ui.components.containers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bakery.ui.messages.LocalMessages
import com.bakery.ui.messages.Messages
import com.bakery.ui.navigation.navigator.LocalNavController
import com.bakery.ui.navigation.navigator.LocalNavigator
import com.bakery.ui.navigation.navigator.LocalTabNavigator
import com.bakery.ui.navigation.navigator.Navigator
import com.bakery.ui.navigation.tab.TabNavigator
import com.bakery.ui.theme.AppTheme
import com.bakery.ui.window.LocalWindowUtils
import com.bakery.ui.window.WindowUtils

@Composable
fun AppContainer(
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {
    ProvidersContainer(
        LocalMessages provides Messages.instance,
        LocalNavigator provides Navigator.instance,
        LocalNavController provides navController,
        LocalTabNavigator provides TabNavigator.instance,
        LocalWindowUtils provides WindowUtils.instance,
    ) {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                content()
            }
        }
    }
}
