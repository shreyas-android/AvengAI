package com.cogniheroid.framework.feature.chat.data.model

sealed class ChatDetailItem(val messageStartDate:String) {

    data class NormalMessage(val startDate:String, val messageItem: MessageItem) : ChatDetailItem(startDate)

    data class ReplyMessage(val startDate:String, val messageItem: MessageItem) : ChatDetailItem(startDate)
}