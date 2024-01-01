package com.cogniheroid.framework.feature.chat.data.model

import android.net.Uri

data class MessageItem(
    val chatListItemId:Long, val itemId:Long, val user:User,
    val messageStatus: UserMessageStatus,
    val isForward: Boolean,
    val messageStartDate: String,
    val message:String,
    val nonTextUri: Uri?,
    val isUser: Boolean, val messageTime:Long, val replyId: Int?)

data class User(val id: Int, val name: String, val image: Uri? = null)
enum class UserMessageStatus {
   ERROR, NOT_SENT, SENT, DELIVERED, READ
}