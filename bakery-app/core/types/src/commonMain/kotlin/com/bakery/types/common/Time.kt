package com.bakery.types.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

object Time {
    val defaultFormat: DateTimeFormat<LocalDateTime> = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        char(' ')
        time(LocalTime.Formats.ISO)
    }

    val defaultDateFormat: DateTimeFormat<LocalDate> = LocalDate.Format {
        dayOfMonth()
        char('/')
        monthNumber()
        char('/')
        year()
    }

    val defaultTimeFormat: DateTimeFormat<LocalTime> = LocalTime.Format {
        hour()
        char(':')
        minute()
        char(':')
        second()
    }

    fun LocalDateTime.formatTime(): String {
        return defaultTimeFormat.format(time)
    }

    fun LocalTime.formatTime(): String {
        return defaultTimeFormat.format(this)
    }

    fun Long.parseDateToString(format: Formats = Formats.DATE): String {
        val datetime: LocalDateTime = Instant
            .fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.UTC)
        return when (format) {
            Formats.DATETIME -> defaultFormat.format(datetime)
            Formats.DATE -> defaultDateFormat.format(datetime.date)
            Formats.TIME -> defaultTimeFormat.format(datetime.time)
        }
    }

    enum class Formats {
        DATETIME,
        DATE,
        TIME,
    }
}
