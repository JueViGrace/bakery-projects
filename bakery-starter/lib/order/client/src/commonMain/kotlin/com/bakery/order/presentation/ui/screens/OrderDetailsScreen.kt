package com.bakery.order.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bakery.core.presentation.ui.components.display.TextComponent
import com.bakery.order.presentation.viewmodel.OrderDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OrderDetailsScreen(viewModel: OrderDetailsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = "Orders Details Screen",
        )
    }
}
