package com.cogniheroid.framework.shared.core.chat

expect class DateTime() {
  
  fun getFormattedDate(
    iso8601Timestamp: Long,
    format: String,
  ): String


  fun getTimeInMilliSecond(
    formattedString: String,
    format: String,
  ): Long
}