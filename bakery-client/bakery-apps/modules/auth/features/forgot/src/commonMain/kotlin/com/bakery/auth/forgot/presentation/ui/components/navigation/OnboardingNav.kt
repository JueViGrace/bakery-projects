package com.bakery.auth.forgot.presentation.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bakery.ui.navigation.ForgotRoute
import com.bakery.ui.navigation.models.ForgotAction
import kotlinx.serialization.json.Json

fun NavGraphBuilder.forgotNav(content: @Composable (action: ForgotAction) -> Unit) {
    composable<ForgotRoute> { backStackEntry ->
        val params: ForgotRoute = backStackEntry.toRoute()
        content(Json.decodeFromString(params.action))
    }
}
