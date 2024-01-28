package com.cogniheroid.framework.shared.core.chat.repository.chatlist

import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import com.cogniheroid.framework.shared.core.chat.datasource.chatlist.ChatListDataSource
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import com.cogniheroid.framework.shared.core.chat.flow.toCommonFlow

class ChatListRepositoryImpl(private val chatListDataSource: ChatListDataSource): ChatListRepository {
    override suspend fun getLastLocalChatId(): Long {
        return chatListDataSource.getLastLocalChatId()
    }

    override fun getConversationEntities(): CommonFlow<List<ConversationEntity>> {
        return chatListDataSource.getChatList().toCommonFlow()
    }

    override suspend fun insertConversationEntity(conversationEntity: ConversationEntity) {
        chatListDataSource.insertChatListItem(conversationEntity)
    }

    override suspend fun updateChatListItem(conversationEntity: ConversationEntity) {
        chatListDataSource.updateChatListItem(conversationEntity)
    }

    override suspend fun updateChatListItemTitle(title: String, chatId: Long) {
        chatListDataSource.updateChatListItemTitle(title, chatId)
    }

    override suspend fun updateChatListItemLastMessageId(lastMessageId: Long, chatId: Long) {
        chatListDataSource.updateChatListItemLastMessageId(lastMessageId, chatId)
    }

    override suspend fun deleteChatListItem(chatId: Long) {
        chatListDataSource.deleteChatListItem(chatId)
    }


}