package com.bakery.auth.signup.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.signup.presentation.events.SignUpEvents
import com.bakery.auth.signup.presentation.state.SignUpState
import com.bakery.auth.signup.presentation.ui.components.SignUpFormSection
import com.bakery.auth.ui.AuthCardTopContent
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.already_have_account
import com.bakery.resources.generated.resources.go_back
import com.bakery.resources.generated.resources.ic_arrow_left
import com.bakery.resources.generated.resources.ic_forms
import com.bakery.resources.generated.resources.sign_up_title
import com.bakery.resources.generated.resources.welcome
import com.bakery.ui.Fonts
import com.bakery.ui.components.buttons.RowCardButton
import com.bakery.ui.components.containers.cards.CardContainer
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.navigation.navigator.LocalNavigator
import com.bakery.ui.navigation.navigator.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// todo: improve form design
@Composable
fun SignUpLayout(
    modifier: Modifier = Modifier,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit
) {
    val navigator: Navigator = LocalNavigator.current
    val scope: CoroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            CardContainer(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 28.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            ) {
                AuthCardTopContent(
                    title = stringResource(Res.string.welcome),
                    subTitle = stringResource(Res.string.sign_up_title),
                    painter = painterResource(Res.drawable.ic_forms),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
                )

                SignUpFormSection(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    onEvent = onEvent,
                )
            }
        }

        item {
            RowCardButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    scope.launch {
                        navigator.navigateUp()
                    }
                },
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright,
                ),
                leadingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_arrow_left),
                        contentDescription = stringResource(Res.string.go_back),
                    )
                },
                content = {
                    Text(
                        text = stringResource(Res.string.already_have_account),
                        style = Fonts.smallTextStyle,
                    )
                },
            )
        }
    }
}
