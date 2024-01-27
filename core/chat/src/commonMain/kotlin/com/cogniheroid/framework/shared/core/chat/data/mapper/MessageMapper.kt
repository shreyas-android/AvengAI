package com.cogniheroid.framework.shared.core.chat.data.mapper

import com.cogniheroid.framework.shared.core.chat.data.entities.MessagesEntity
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageContentType
import com.cogniheroid.framework.shared.core.chat.data.enum.MessageType
import com.cogniheroid.framework.shared.core.chat.data.enum.ReadStatusType
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import migrations.Messages


fun Messages.toMessageEntity(): MessagesEntity {
    return MessagesEntity( messageId=messageId, chatId= chatId,
        senderId= senderId, message= message,
        messageStartDate= messageStartDate,
        messageTime= messageTime,readStatus=  readStatus ?: ReadStatusType.PENDING.value,
        messageType= messageType?:MessageType.NORMAL.value, messageContentType= messageContentType ?: MessageContentType.TEXT.value,
        replyMessageId=  replyMessageId, fileUri = fileUri)
}

fun MessagesEntity.toMessageWithSender(senderName:String, senderImageURI:String?=null): MessageWithSender {
    return MessageWithSender(messageId = messageId, chatId = chatId, senderId = senderId,
        message = message,
        messageContentType = messageContentType.toMessageContentType(), senderName = senderName,
        messageStartDate = messageStartDate, messageTime = messageTime, replyMessageId = replyMessageId,
        fileUri = fileUri, readStatus = readStatus.toReadStatusType(), messageType = messageType
            .toMessageType(), senderImageUri = senderImageURI)
}

fun Int.toMessageContentType(): MessageContentType {
  return  when(this){
        1 -> MessageContentType.TEXT
        2 -> MessageContentType.IMAGE
        3 -> MessageContentType.VIDEO
        4 -> MessageContentType.AUDIO
        5 -> MessageContentType.DOCUMENT
        6 -> MessageContentType.LOCATION
        7 -> MessageContentType.CONTACT
        8 -> MessageContentType.STICKER
        9 -> MessageContentType.LINK
        else -> MessageContentType.UNKNOWN

    }
}

fun Int.toReadStatusType():ReadStatusType{
    return when(this){
        1 -> ReadStatusType.READ
        2 -> ReadStatusType.UNREAD
        3 -> ReadStatusType.DELIVERED
        else -> ReadStatusType.PENDING
    }
}

fun Int.toMessageType():MessageType{
    return when(this){
        1 -> MessageType.NORMAL
        2 -> MessageType.REPLY
        3 -> MessageType.FORWARD
        else -> MessageType.NORMAL
    }
}