package com.cogniheroid.framework.shared.core.chat.utils

import com.cogniheroid.framework.shared.core.chat.DateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

object DateTimeUtils {

    private const val DATE_FORMAT = "ddMMyyyy"
    fun getStartDay(startDateTime:Long): String {
        val dateTime = DateTime()
        return dateTime.getFormattedDate(startDateTime, DATE_FORMAT)
    }

    fun formatDate(startDateTime: Long, format:String): String {
        val dateTime = DateTime()
        return dateTime.getFormattedDate(startDateTime, format)
    }

    fun getDateAsMilliSecond(formattedString:String): Long {
        val dateTime = DateTime()
        return dateTime.getTimeInMilliSecond(formattedString, DATE_FORMAT)
    }
}