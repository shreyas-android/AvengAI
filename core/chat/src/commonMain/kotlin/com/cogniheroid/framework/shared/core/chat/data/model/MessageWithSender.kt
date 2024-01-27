package com.cogniheroid.framework.shared.core.chat.data.model

import com.cogniheroid.framework.shared.core.chat.data.enum.MessageContentType
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageType
import com.cogniheroid.framework.shared.core.chat.data.enum.ReadStatusType

data class MessageWithSender(val messageId:Long, val chatId:Long,
                             val senderId:Long, val senderName:String, val message:String?,
                             val senderImageUri:String?,
                             val fileUri:String?,
                             val messageStartDate:String,
                             val messageTime:Long, val readStatus:ReadStatusType,
                             val messageType:MessageType, val messageContentType:MessageContentType,
                             val replyMessageId:Long?)