package com.bakery.auth.signup.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.bakery.auth.ui.AuthCardSection
import com.bakery.auth.ui.AuthCardTopContent
import com.bakery.auth.ui.AuthPortraitLayout
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.already_have_account
import com.bakery.resources.generated.resources.go_back
import com.bakery.resources.generated.resources.ic_arrow_left
import com.bakery.resources.generated.resources.ic_forms
import com.bakery.resources.generated.resources.sign_up_title
import com.bakery.resources.generated.resources.welcome
import com.bakery.ui.Fonts
import com.bakery.ui.components.buttons.RowCardButton
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.navigation.navigator.LocalNavigator
import com.bakery.ui.navigation.navigator.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpPortraitLayout(
    modifier: Modifier = Modifier,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit
) {
    val navigator: Navigator = LocalNavigator.current
    val scope: CoroutineScope = rememberCoroutineScope()

    AuthPortraitLayout(
        modifier = modifier,
    ) {
        item {
            AuthCardSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
            ) {
                AuthCardTopContent(
                    title = stringResource(Res.string.welcome),
                    subTitle = stringResource(Res.string.sign_up_title),
                    painter = painterResource(Res.drawable.ic_forms),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                )

                // todo: form section

                RowCardButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    },
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
}
