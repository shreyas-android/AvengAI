package com.cogniheroid.framework.shared.core.chat.data.mapper

import com.cogniheroid.framework.shared.core.chat.data.entities.SenderEntity
import migrations.Sender

fun Sender.toSenderInfo(): SenderEntity {
   return SenderEntity(senderId = senderId, senderName = senderName, senderImageUri = senderImageUri, isUser = isUser ?: false)
}