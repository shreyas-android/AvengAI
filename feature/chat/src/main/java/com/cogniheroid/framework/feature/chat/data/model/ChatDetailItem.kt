package com.cogniheroid.framework.feature.chat.data.model

import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender

sealed class ChatDetailItem(val messageStartDate:String) {

    data class NormalMessage(val startDate:String, val messageItem: MessageWithSender) : ChatDetailItem(startDate)

    data class ReplyMessage(val startDate:String, val messageItem: MessageWithSender) : ChatDetailItem(startDate)
}