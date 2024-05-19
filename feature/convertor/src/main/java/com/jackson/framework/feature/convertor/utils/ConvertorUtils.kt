package com.jackson.framework.feature.convertor.utils

import com.jackson.framework.feature.convertor.ui.datetimeconvertor.data.model.TimeZoneInfo
import com.sparrow.framework.core.avengerad.AvengerAdCore
import java.util.TimeZone

object ConvertorUtils {



    private const val TEST_AD_UNIT_ID_BANNER = "ca-app-pub-3940256099942544/6300978111"
    private const val CONVERTOR_BANNER_1 = "ca-app-pub-7235511025002252/8754484573"
    private const val CONVERTOR_BANNER_2 = "ca-app-pub-7235511025002252/1805932846"

    private fun isDebuggable(): Boolean {
        return AvengerAdCore.isAdDebug

    }

    fun getConvertorBannerAd1() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            CONVERTOR_BANNER_1
        }
    }

    fun getConvertorBannerAd2() : String {
        return if(isDebuggable()){
            TEST_AD_UNIT_ID_BANNER
        }else{
            CONVERTOR_BANNER_2
        }
    }



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
            timeInMillis = milliSecond, requiredFormat = DATE_FORMAT)
    }

    fun getFormattedTime(milliSecond: Long): String {
        return CalendarUtils.formatDateTime(
            timeInMillis = milliSecond, requiredFormat = TIME_FORMAT)
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