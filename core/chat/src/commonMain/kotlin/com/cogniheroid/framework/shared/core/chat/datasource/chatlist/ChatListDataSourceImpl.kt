package com.cogniheroid.framework.shared.core.chat.datasource.chatlist

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.cogniheroid.framework.shared.core.chat.data.mapper.toChatListItem
import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import kotlinx.coroutines.flow.Flow
import database.ConversationQueries
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class ChatListDataSourceImpl(private val dispatcher:CoroutineContext,
                             private val conversationQueries: ConversationQueries):ChatListDataSource {
    override suspend fun getLastLocalChatId(): Long {
        return conversationQueries.getLastLocalChatId().executeAsOne().MAX ?: 1L
    }

    override fun getChatList(): Flow<List<ConversationEntity>> {
        return conversationQueries.getChatList().asFlow().mapToList(dispatcher).map {
            it.map {chatList ->
                chatList.toChatListItem()
            }
        }
    }

    override suspend fun insertChatListItem(conversationEntity: ConversationEntity) {
        conversationQueries.insertChatList(conversationEntity.id, conversationEntity.title,
            conversationEntity.imageUri, conversationEntity.lastMessageId, conversationEntity.unreadCount)
    }

    override suspend fun updateChatListItemTitle(title: String, chatId: Long) {
        conversationQueries.updateChatListItemTitle(title, chatId)
    }

    override suspend fun updateChatListItemLastMessageId(lastMessageId: Long, chatId: Long) {
        conversationQueries.updateLastMessageId(lastMessageId, chatId)
    }

    override suspend fun updateChatListItem(conversationEntity: ConversationEntity) {
        conversationQueries.updateChatListItem(conversationEntity.title, conversationEntity.imageUri,
            conversationEntity.lastMessageId, conversationEntity.unreadCount, conversationEntity.id)
    }

    override suspend fun deleteChatListItem(chatId: Long) {
        conversationQueries.deleteChatList(chatId)
    }
}