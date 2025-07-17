package com.bakery.auth.ui

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.log_in
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.IconComponent
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthCardTopContent(
    title: String,
    subTitle: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentPadding: PaddingValues = PaddingValues(),
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
            IconComponent(
                modifier = Modifier
                    .padding(8.dp)
                    .size(42.dp),
                painter = painter,
                contentDescription = stringResource(Res.string.log_in),
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = Fonts.largeTextStyle,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = subTitle,
                    style = Fonts.defaultTextStyle,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
