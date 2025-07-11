package com.bakery.ui.components.display.filters

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.end_date
import com.bakery.resources.generated.resources.filter_by_date
import com.bakery.resources.generated.resources.initial_date
import com.bakery.ui.Fonts
import com.bakery.ui.components.display.sections.SectionTitle
import com.bakery.ui.components.input.DateField
import com.bakery.ui.dates.Dates
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDateFilter(
    initialDate: String,
    targetDate: String,
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.filter_by_date),
    onInitialDateChange: (dateMillis: Long) -> Unit = {},
    onTargetDateChange: (dateMillis: Long) -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier,
    ) {
        SectionTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            title = title,
            textStyle = Fonts.defaultTextStyle,
        ) {
            DateField(
                modifier = Modifier.fillMaxWidth(),
                date = initialDate,
                onDateChange = onInitialDateChange,
                labelText = stringResource(Res.string.initial_date),
                selectableDates = Dates.rangeSelectableDates(isInitialDate = true),
            )
            DateField(
                modifier = Modifier.fillMaxWidth(),
                date = targetDate,
                onDateChange = onTargetDateChange,
                labelText = stringResource(Res.string.end_date),
                selectableDates = Dates.rangeSelectableDates(isTargetDate = true),
            )
        }
    }
}
