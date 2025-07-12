package com.bakery.auth.signin.presentation.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.auth.signin.presentation.state.SignInState
import com.bakery.auth.signin.presentation.ui.components.SignInFormSection
import com.bakery.auth.ui.AuthCardSection
import com.bakery.auth.ui.AuthCardTopContent
import com.bakery.auth.ui.AuthPortraitLayout
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.dont_have_account
import com.bakery.resources.generated.resources.go_forward
import com.bakery.resources.generated.resources.google
import com.bakery.resources.generated.resources.google_logo
import com.bakery.resources.generated.resources.ic_chevron_right
import com.bakery.resources.generated.resources.ic_login
import com.bakery.resources.generated.resources.or_sign_in_with
import com.bakery.resources.generated.resources.sign_in_title
import com.bakery.resources.generated.resources.welcome
import com.bakery.ui.Fonts
import com.bakery.ui.components.buttons.CardButton
import com.bakery.ui.components.buttons.RowCardButton
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.ImageComponent
import com.bakery.ui.model.SocialProvider
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInPortraitLayout(
    state: SignInState,
    onEvent: (SignInEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
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
                    subTitle = stringResource(Res.string.sign_in_title),
                    painter = painterResource(Res.drawable.ic_login),
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                )

                SignInFormSection(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    onEvent = onEvent,
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    HorizontalDivider()

                    Text(
                        text = stringResource(Res.string.or_sign_in_with),
                        style = Fonts.smallTextStyle,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
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

                    HorizontalDivider()

                    RowCardButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onEvent(SignInEvents.OnSignUp)
                        },
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
                                painter = painterResource(Res.drawable.ic_chevron_right),
                                contentDescription = stringResource(Res.string.go_forward),
                            )
                        }
                    )
                }
            }
        }
    }
}
