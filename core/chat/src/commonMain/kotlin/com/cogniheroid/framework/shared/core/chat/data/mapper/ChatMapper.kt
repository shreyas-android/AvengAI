package com.cogniheroid.framework.shared.core.chat.data.mapper

import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import migrations.Conversation

fun Conversation.toChatListItem(): ConversationEntity {
    return ConversationEntity(id = id, title = title, imageUri = imageUri,
        lastMessageId = lastMessageId, unreadCount = unreadCount ?: 0)
}