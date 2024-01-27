package com.cogniheroid.framework.shared.core.chat

import platform.Foundation.*

actual class DateTime {
  
  actual fun getFormattedDate(
    iso8601Timestamp: Long,
    format: String,
  ): String {
    /*val date = getDateFromIso8601Timestamp(iso8601Timestamp) ?: return ""
  
    val dateFormatter = NSDateFormatter()
    dateFormatter.timeZone = NSTimeZone.localTimeZone
    dateFormatter.locale = NSLocale.autoupdatingCurrentLocale
    dateFormatter.dateFormat = format
    return dateFormatter.st(date)*/
    return ""
  }
  
  private fun getDateFromIso8601Timestamp(string: String): NSDate? {
    return NSISO8601DateFormatter().dateFromString(string)
  }

  actual fun getTimeInMilliSecond(
    formattedString: String,
    format: String,
  ): Long{
    return 1L
  }
}