package com.cogniheroid.framework.shared.core.chat.data.entities

data class MessagesEntity(
    val messageId: Long, val chatId: Long,
    val senderId: Long, val message: String?, val fileUri:String?,
    val messageStartDate: String,
    val messageTime: Long, val readStatus: Int,
    val messageType: Int, val messageContentType: Int,
    val replyMessageId: Long?
)