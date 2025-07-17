package com.bakery.ui.dates

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object Dates {
    @OptIn(ExperimentalMaterial3Api::class)
    val selectableDatesFromToday: SelectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            val utcTime: LocalDateTime = Instant.fromEpochMilliseconds(utcTimeMillis).toLocalDateTime(TimeZone.UTC)
            return utcTime.date >= currentTime.date
        }

        override fun isSelectableYear(year: Int): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            return year >= currentTime.year
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    val selectableDatesBeforeToday: SelectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            val utcTime: LocalDateTime = Instant.fromEpochMilliseconds(utcTimeMillis).toLocalDateTime(TimeZone.UTC)
            return utcTime.date <= currentTime.date
        }

        override fun isSelectableYear(year: Int): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            return year <= currentTime.year
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
    fun rangeSelectableDates(
        isInitialDate: Boolean = false,
        isTargetDate: Boolean = false,
    ): SelectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            val utcTime: LocalDateTime = Instant.fromEpochMilliseconds(utcTimeMillis).toLocalDateTime(TimeZone.UTC)
            return when {
                isInitialDate -> {
                    utcTime.date <= currentTime.date
                }
                isTargetDate -> {
                    utcTime.date >= currentTime.date
                }
                else -> {
                    utcTime.date == currentTime.date
                }
            }
        }

        override fun isSelectableYear(year: Int): Boolean = true
    }
}
