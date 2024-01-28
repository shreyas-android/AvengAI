package com.cogniheroid.framework.shared.core.chat.manager.chatlist

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow

interface ChatListManager {

    companion object{

        fun getInstance(): ChatListManager {
            return ChatDomainCore.instance.chatListManager
        }
    }

   suspend fun getLatestLocalChatId(): Long

    suspend fun insertAndGetNewConversationItem(title:String, imageUri:String?): ConversationItem

    fun getConversationItems(): CommonFlow<List<ConversationItem>>

    suspend fun insertChatListItem(conversationEntity: ConversationEntity)

    suspend fun updateChatListItem(conversationEntity: ConversationEntity)

    suspend fun updateChatListItemTitle(title:String, chatId:Long)

    suspend fun updateChatListItemLastMessageId(lastMessageId:Long, chatId:Long)

    suspend fun deleteChatListItem(chatId:Long)

    suspend fun deleteMessagesByChatId(chatId:Long)
}