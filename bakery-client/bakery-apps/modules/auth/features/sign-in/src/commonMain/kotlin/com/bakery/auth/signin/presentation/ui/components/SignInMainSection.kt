package com.bakery.auth.signin.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.ui.setMaxWidth

@Composable
fun SignInMainSection(
    modifier: Modifier = Modifier,
    state: SignInState,
    onEvent: (SignInEvents) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        ElevatedCard(
            modifier = Modifier.setMaxWidth(600),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 18.dp,
                        vertical = 48.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SignInTopContent(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                    onEvent = onEvent,
                )

                SignInFormSection(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    onEvent = onEvent,
                )
            }
        }
    }
}
