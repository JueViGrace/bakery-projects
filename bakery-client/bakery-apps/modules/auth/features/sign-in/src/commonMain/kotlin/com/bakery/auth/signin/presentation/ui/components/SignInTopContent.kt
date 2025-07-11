package com.bakery.auth.signin.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.bakery.auth.signin.presentation.events.SignInEvents
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.go_to_sign_up
import com.bakery.resources.generated.resources.ic_arrow_right
import com.bakery.resources.generated.resources.ic_login
import com.bakery.resources.generated.resources.log_in
import com.bakery.resources.generated.resources.sign_in_title
import com.bakery.resources.generated.resources.welcome
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.ImageComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInTopContent(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentPadding: PaddingValues = PaddingValues(),
    onEvent: (SignInEvents) -> Unit
) {
    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = contentColorFor(MaterialTheme.colorScheme.inverseSurface)
            )
        ) {
            ImageComponent(
                modifier = Modifier
                    .padding(8.dp)
                    .size(42.dp),
                painter = painterResource(Res.drawable.ic_login),
                contentDescription = stringResource(Res.string.log_in)
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.welcome),
                style = Fonts.largeTextStyle,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${stringResource(Res.string.sign_in_title)}, ",
                    style = Fonts.defaultTextStyle,
                )
                Row(
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            onClick = {
                                onEvent(SignInEvents.OnSignUp)
                            },
                            role = Role.Button
                        ),
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(Res.string.go_to_sign_up).lowercase(),
                    )
                    IconComponent(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(Res.drawable.ic_arrow_right),
                        contentDescription = stringResource(Res.string.go_to_sign_up)
                    )
                }
            }
        }
    }
}
