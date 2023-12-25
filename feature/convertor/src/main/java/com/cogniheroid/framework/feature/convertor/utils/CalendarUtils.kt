package com.cogniheroid.framework.feature.convertor.utils

import com.cogniheroid.framework.feature.convertor.ui.datetimeconvertor.data.model.TimeZoneInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CalendarUtils {

    fun formatDateTime(
        timeInMillis: Long,
        currentTimeZoneId: String? = null,
        requiredFormat: String,
        requiredTimeZoneId: String? = null,
        restrictOnlyEnglish: Boolean = false
    ): String {
        var parsedDate: Date? = null
        if (currentTimeZoneId != null) {
            val calendar = Calendar.getInstance()
            calendar.timeZone = TimeZone.getTimeZone(currentTimeZoneId)
            calendar.timeInMillis = timeInMillis
            parsedDate = calendar.time
        }
        val locale = if (restrictOnlyEnglish) {
            Locale.ENGLISH
        } else {
            Locale.getDefault()
        }

        val requiredSimpleDateFormat = SimpleDateFormat(requiredFormat, locale)
        if (requiredTimeZoneId != null) {
            requiredSimpleDateFormat.timeZone = TimeZone.getTimeZone(requiredTimeZoneId)
        }
        return if (parsedDate != null) {
            requiredSimpleDateFormat.format(parsedDate)
        } else {
            requiredSimpleDateFormat.format(Date(timeInMillis))
        }
    }

    fun createGmtOffsetString(includeGmt: Boolean,
                              includeMinuteSeparator: Boolean, offsetMillis: Int): String {
        var offsetMinutes = offsetMillis / 60000
        var sign = '+'
        if (offsetMinutes < 0) {
            sign = '-'
            offsetMinutes = -offsetMinutes
        }
        val builder = StringBuilder(9)
        if (includeGmt) {
            builder.append("GMT")
        }
        builder.append(sign)
        appendNumber(builder, 2, offsetMinutes / 60)
        if (includeMinuteSeparator) {
            builder.append(':')
        }
        appendNumber(builder, 2, offsetMinutes % 60)
        return builder.toString()
    }

    private fun appendNumber(builder: StringBuilder, count: Int = 2, value: Int) {
        val string = value.toString()
        for (i in 0 until count - string.length) {
            builder.append('0')
        }
        builder.append(string)
    }

    private fun getTimeZoneCountryName(timeZoneId: String): String? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val timeZone = android.icu.util.TimeZone.getTimeZone(timeZoneId)
            return timeZone.getDisplayName(false, android.icu.util.TimeZone
                .GENERIC_LOCATION).replace("TIME", "", true)
        }
        return null
    }

    fun getTimeZoneInfo(timeZoneId: String): TimeZoneInfo {
        val timeZone = TimeZone.getTimeZone(timeZoneId)
        val timeZoneCode = createGmtOffsetString(includeGmt = true,
            includeMinuteSeparator = true, offsetMillis = timeZone.rawOffset)
        return TimeZoneInfo(timeZoneId, getTimeZoneCountryName(timeZoneId),
            timeZone.displayName, timeZoneCode)
    }
}