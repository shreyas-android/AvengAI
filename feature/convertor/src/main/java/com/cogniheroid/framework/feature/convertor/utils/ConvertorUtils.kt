package com.cogniheroid.framework.feature.convertor.utils

import com.cogniheroid.framework.feature.convertor.ui.datetimeconvertor.data.model.TimeZoneInfo
import java.util.TimeZone

object ConvertorUtils {

    private const val DATE_FORMAT = "EEE, dd MMM yyyy"
    private const val TIME_FORMAT = "hh:mm a"

    const val BANNER_AD_UNIT_ID_1 = "9e33f44609ecdc5b"
    const val BANNER_AD_UNIT_ID_2 = "9e33f44609ecdc5b"
    const val BANNER_AD_UNIT_ID_3 = "9e33f44609ecdc5b"
    const val BANNER_AD_UNIT_ID_4 = "9e33f44609ecdc5b"
    const val BANNER_AD_UNIT_ID_5 = "9e33f44609ecdc5b"
    const val BANNER_AD_UNIT_ID_6 = "9e33f44609ecdc5b"

    const val MREC_AD_UNIT_ID_1 = "be2e9fb9c3edb0cc"
    const val MREC_AD_UNIT_ID_2 = "be2e9fb9c3edb0cc"
    const val MREC_AD_UNIT_ID_3 = "be2e9fb9c3edb0cc"
    const val MREC_AD_UNIT_ID_4 = "be2e9fb9c3edb0cc"
    const val MREC_AD_UNIT_ID_5 = "be2e9fb9c3edb0cc"
    const val MREC_AD_UNIT_ID_6 = "be2e9fb9c3edb0cc"

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