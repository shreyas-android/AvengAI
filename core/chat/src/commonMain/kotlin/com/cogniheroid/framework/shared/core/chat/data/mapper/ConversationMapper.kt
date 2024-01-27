package com.cogniheroid.framework.shared.core.chat.data.mapper

import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem


fun ConversationEntity.toConversationItem(
    lastMessage: String?,
    lastMessageTime: Long?
): ConversationItem {
    return ConversationItem(
        id = id,
        title = title,
        imageUri = imageUri,
        lastMessage = lastMessage,
        lastMessageTime = lastMessageTime,
        unreadCount = unreadCount
    )
}
