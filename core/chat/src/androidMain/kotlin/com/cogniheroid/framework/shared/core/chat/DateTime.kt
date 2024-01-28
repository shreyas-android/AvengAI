package com.cogniheroid.framework.shared.core.chat

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

actual class DateTime {

    actual fun getFormattedDate(
        iso8601Timestamp: Long,
        format: String,
    ): String {
        val date = java.time.LocalDateTime.ofInstant(
            Instant.ofEpochMilli(iso8601Timestamp),
            TimeZone.getDefault().toZoneId()
        );

        val formatter = DateTimeFormatter.ofPattern(format)
        return date.format(formatter)
    }

    actual fun getTimeInMilliSecond(formattedString: String, format: String): Long {
        /*val formatter = DateTimeFormatter.ofPattern(format)
        val localDateTime = LocalDate.parse(formattedString, formatter)
        return localDateTime.toEpochDay()*/
        val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.parse(formattedString)?.time ?: 1L
    }
}