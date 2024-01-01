package com.cogniheroid.framework.feature.chat.data.model

import android.net.Uri

data class ChatListItem(
    val itemId: Long,
    val title: String,
    val subtitle: String,
    val lastMessageMillis: Long,
    val unreadCount: Int,
    val imageUri: Uri? = null,
    val messageItems: List<MessageItem> = emptyList()
)