package com.androidai.galaxy.ad.utils

import java.util.Calendar

object CalendarUtils {

    fun getCalendar(timeInMillis:Long?= null): Calendar {
        val calendar = Calendar.getInstance()

        timeInMillis?.let {
            calendar.timeInMillis = it
        }

        return calendar
    }
}