package com.cogniheroid.framework.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object CalendarUtils {

    const val FORMAT_WEEK_DAY_DATE = "EEE"
    const val FORMAT_MONTH_DATE = "MMM dd"
    const val FORMAT_DATE = "dd/MM/yyyy"
    const val FORMAT_TIME = "hh:mm a"

    fun isSameDay(calendar: Calendar, calendar1: Calendar): Boolean {
        return calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH)
    }

    fun formatDate(calendar: Calendar, format:String): String {
        val dateTimeFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateTimeFormat.format(calendar.time)
    }

    fun isCurrentYear(calendar: Calendar): Boolean {
        val currentCalendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
    }

    fun isCurrentMonth(calendar: Calendar): Boolean {
        val currentCalendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)
    }

    fun isCurrentWeekDay(calendar: Calendar): Boolean {
        val currentCalendar = Calendar.getInstance()
        return calendar.get(Calendar.WEEK_OF_MONTH) == currentCalendar.get(Calendar.WEEK_OF_MONTH)
    }
}