package com.bakery.auth.signin.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.auth.signin.presentation.ui.components.form.SignInFormSection
import com.bakery.auth.ui.AuthCardTopContent
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.dont_have_account
import com.bakery.resources.generated.resources.go_forward
import com.bakery.resources.generated.resources.google
import com.bakery.resources.generated.resources.google_logo
import com.bakery.resources.generated.resources.ic_arrow_right
import com.bakery.resources.generated.resources.ic_login
import com.bakery.resources.generated.resources.or_sign_in_with
import com.bakery.resources.generated.resources.sign_in_title
import com.bakery.resources.generated.resources.welcome
import com.bakery.ui.Fonts
import com.bakery.ui.components.buttons.CardButton
import com.bakery.ui.components.buttons.RowCardButton
import com.bakery.ui.components.containers.cards.CardContainer
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.ImageComponent
import com.bakery.ui.model.SocialProvider
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInLayout(
    state: SignInState,
    onEvent: (SignInEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
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
                    vertical = 28.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            ) {
                AuthCardTopContent(
                    title = stringResource(Res.string.welcome),
                    subTitle = stringResource(Res.string.sign_in_title),
                    painter = painterResource(Res.drawable.ic_login),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
                )

                SignInFormSection(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    onEvent = onEvent,
                )

                Text(
                    text = stringResource(Res.string.or_sign_in_with),
                    style = Fonts.smallTextStyle,
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CardButton(
                        onClick = {
                            onEvent(SignInEvents.OnSocialLogin(SocialProvider.Google))
                        },
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceBright,
                        )
                    ) {
                        ImageComponent(
                            modifier = Modifier
                                .padding(
                                    horizontal = 24.dp,
                                    vertical = 8.dp,
                                )
                                .size(24.dp),
                            painter = painterResource(Res.drawable.google_logo),
                            contentDescription = stringResource(Res.string.google),
                        )
                    }
                }
            }
        }

        item {
            RowCardButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(SignInEvents.OnSignUp)
                },
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright,
                ),
                content = {
                    Text(
                        text = stringResource(Res.string.dont_have_account),
                        style = Fonts.smallTextStyle,
                    )
                },
                trailingIcon = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_arrow_right),
                        contentDescription = stringResource(Res.string.go_forward),
                    )
                }
            )
        }
    }
}
