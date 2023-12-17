package com.configheroid.framework.feature.convertor.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.cogniheroid.framework.feature.convertor.R
import com.configheroid.framework.feature.convertor.datetimeconvertor.data.model.TimeZoneInfo
import java.util.Random
import java.util.TimeZone

object ConvertorUtils {

    private const val DATE_FORMAT = "EEE, dd MMM yyyy"
    private const val TIME_FORMAT = "hh:mm a"

    fun shareContent(context: Context, data: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, data)
            type = "text/plain"
        }
        val title = context.getString(R.string.label_share_content)
        val chooser = Intent.createChooser(intent, title)
        context.startActivity(chooser)
    }

    private fun copyContentToClipboard(context: Context, data: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val data = ClipData.newPlainText(Random().nextLong().toString(), data)
        clipboard.setPrimaryClip(data)
    }

    fun copyAndShowToast(context: Context, result: String, toastMessage:String) {
        copyContentToClipboard(
            context = context,
            data = result
        )
        Toast
            .makeText(context, toastMessage, Toast.LENGTH_SHORT)
            .show()
    }

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