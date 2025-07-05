package com.bakery.ui.components.display.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.address
import com.bakery.resources.generated.resources.map_location
import com.bakery.resources.generated.resources.postal_code
import com.bakery.resources.generated.resources.precision
import com.bakery.resources.generated.resources.your_current_location
import com.bakery.types.common.roundWithDecimals
import com.bakery.types.location.Location
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.ImageComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    location: Location,
) {
    ElevatedCard(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = location.city,
                    style = Fonts.mediumTextStyle,
                    maxLines = 1,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${stringResource(Res.string.address)}:",
                        style = Fonts.labelTextStyle,
                    )
                    Text(
                        text = location.streetAddress,
                        style = Fonts.labelTextStyle,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 1,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${stringResource(Res.string.postal_code)}:",
                        style = Fonts.labelTextStyle,
                    )
                    Text(
                        text = location.zipCode,
                        style = Fonts.labelTextStyle,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 1,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${stringResource(Res.string.precision)}:",
                        style = Fonts.labelTextStyle,
                    )
                    Text(
                        text = "${location.precision.roundWithDecimals()}m",
                        style = Fonts.labelTextStyle,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 1,
                    )
                }
            }
            ImageComponent(
                modifier = Modifier
                    .weight(0.4f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(Res.drawable.map_location),
                contentDescription = stringResource(Res.string.your_current_location),
                contentScale = ContentScale.Crop
            )
        }
    }
}
