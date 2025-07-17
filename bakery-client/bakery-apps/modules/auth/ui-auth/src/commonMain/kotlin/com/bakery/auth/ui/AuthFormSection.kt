package com.bakery.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.ic_arrow_left
import com.bakery.resources.generated.resources.ic_arrow_right
import com.bakery.resources.generated.resources.next
import com.bakery.resources.generated.resources.previous
import com.bakery.ui.components.display.IconComponent
import com.bakery.ui.components.display.TextComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthFormSection(
    fields: @Composable PagerScope.(index: Int) -> Unit,
    submitContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    pageCount: Int = 1,
    nextEnabled: Boolean = true,
) {
    val pagerState: PagerState = rememberPagerState(pageCount = { pageCount })
    val scope: CoroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (pagerState.currentPage > 0) {
                TextButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconComponent(
                            painter = painterResource(Res.drawable.ic_arrow_left),
                            contentDescription = stringResource(Res.string.previous),
                        )
                        TextComponent(
                            text = stringResource(Res.string.previous),
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                                maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            )
                        )
                    }
                }
            }
            if (pagerState.currentPage < pagerState.pageCount - 1) {
                TextButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    enabled = nextEnabled,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextComponent(
                            text = stringResource(Res.string.next),
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                                maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            )
                        )
                        IconComponent(
                            painter = painterResource(Res.drawable.ic_arrow_right),
                            contentDescription = stringResource(Res.string.next),
                        )
                    }
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            pageSpacing = 8.dp
        ) { _ ->
            fields(pagerState.currentPage)
        }
        if (pagerState.pageCount - 1 == pagerState.currentPage) {
            submitContent()
        }
    }
}
