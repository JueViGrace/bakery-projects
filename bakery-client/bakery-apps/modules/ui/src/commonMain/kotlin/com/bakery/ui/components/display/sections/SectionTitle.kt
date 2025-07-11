package com.bakery.ui.components.display.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.ic_arrow_right
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.RowDisplay
import org.jetbrains.compose.resources.painterResource

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    onTitleClick: () -> Unit = {},
    textStyle: TextStyle = Fonts.mediumTextStyle.copy(fontWeight = FontWeight.Bold),
    titlePadding: PaddingValues = PaddingValues(0.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            RowDisplay(
                modifier = Modifier.clip(RoundedCornerShape(16)),
                contentPadding = titlePadding,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                content = {
                    Text(
                        text = title,
                        style = textStyle,
                        maxLines = 1,
                    )
                },
                trailingContent = {
                    IconComponent(
                        painter = painterResource(Res.drawable.ic_arrow_right),
                        contentDescription = "Go to quick sign in",
                    )
                },
                onClick = onTitleClick,
            )
        }
        content()
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle = Fonts.mediumTextStyle.copy(fontWeight = FontWeight.Bold),
    titlePadding: PaddingValues = PaddingValues(0.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RowDisplay(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = titlePadding,
            horizontalArrangement = Arrangement.Start,
            content = {
                Text(
                    text = title,
                    style = textStyle,
                    maxLines = 1,
                )
            },
        )
        content()
    }
}
