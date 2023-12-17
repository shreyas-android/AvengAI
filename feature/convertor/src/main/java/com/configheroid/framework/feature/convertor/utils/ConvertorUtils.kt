package com.configheroid.framework.feature.convertor.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import com.cogniheroid.framework.feature.convertor.R
import com.configheroid.framework.feature.convertor.ui.datetimeconvertor.data.model.TimeZoneInfo
import java.util.Random
import java.util.TimeZone

object ConvertorUtils {

    private const val DATE_FORMAT = "EEE, dd MMM yyyy"
    private const val TIME_FORMAT = "hh:mm a"

    fun getFormattedDate(milliSecond: Long): String {
        return CalendarUtils.formatDateTime(
            timeInMillis = milliSecond,
            requiredFormat = DATE_FORMAT)
    }

    fun getFormattedTime(milliSecond: Long): String {
        return CalendarUtils.formatDateTime(
            timeInMillis = milliSecond,
            requiredFormat = TIME_FORMAT)
    }

    fun getFormattedDateAndTime(milliSecond: Long): String {
        val date = getFormattedDate(milliSecond)
        val time = getFormattedTime(milliSecond)

        return "$date $time"
    }

    fun getCurrentTimeZoneInfo(): TimeZoneInfo {
        val timeZoneId = TimeZone.getDefault().id
        return CalendarUtils.getTimeZoneInfo(timeZoneId)
    }
}