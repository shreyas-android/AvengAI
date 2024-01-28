package com.cogniheroid.framework.shared.core.chat.datasource.chatlist

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import kotlinx.coroutines.flow.Flow


interface ChatListDataSource {

    companion object{
        fun getInstance(): ChatListDataSource {
            return ChatDomainCore.instance.chatListDataSource
        }
    }

    suspend fun getLastLocalChatId(): Long

    fun getChatList(): Flow<List<ConversationEntity>>
    suspend fun insertChatListItem(conversationEntity: ConversationEntity)

    suspend fun updateChatListItemTitle(title:String, chatId:Long)

    suspend fun updateChatListItemLastMessageId(lastMessageId:Long, chatId:Long)

    suspend fun updateChatListItem(conversationEntity: ConversationEntity)

    suspend fun deleteChatListItem(chatId: Long)
}