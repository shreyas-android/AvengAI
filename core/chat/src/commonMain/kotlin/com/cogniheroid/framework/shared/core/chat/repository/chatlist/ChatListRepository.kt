package com.cogniheroid.framework.shared.core.chat.repository.chatlist

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow

interface ChatListRepository {

    companion object{

            fun getInstance(): ChatListRepository {
                return ChatDomainCore.instance.chatListRepository
            }
    }

   suspend fun getLastLocalChatId(): Long

    fun getConversationEntities(): CommonFlow<List<ConversationEntity>>

    suspend fun insertConversationEntity(conversationEntity: ConversationEntity)

    suspend fun updateChatListItem(conversationEntity: ConversationEntity)

    suspend fun updateChatListItemTitle(title:String, chatId:Long)

    suspend fun updateChatListItemLastMessageId(lastMessageId:Long, chatId:Long)

    suspend fun deleteChatListItem(chatId: Long)
}