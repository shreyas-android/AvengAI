package com.cogniheroid.framework.shared.core.chat.manager.chatlist

import com.cogniheroid.framework.shared.core.chat.UniqueIdGenerator
import com.cogniheroid.framework.shared.core.chat.data.mapper.toConversationItem
import com.cogniheroid.framework.shared.core.chat.data.entities.ConversationEntity
import com.cogniheroid.framework.shared.core.chat.data.model.ConversationItem
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import com.cogniheroid.framework.shared.core.chat.flow.toCommonFlow
import com.cogniheroid.framework.shared.core.chat.repository.chatlist.ChatListRepository
import com.cogniheroid.framework.shared.core.chat.repository.message.MessageRepository
import kotlinx.coroutines.flow.map

class ChatListManagerImpl(private val chatListRepository: ChatListRepository,
                          private val messageRepository: MessageRepository):ChatListManager {

    private val localChatIdGenerator = UniqueIdGenerator {
        chatListRepository.getLastLocalChatId()
    }

    override suspend fun getLatestLocalChatId(): Long {
        return localChatIdGenerator.get()
    }

    override suspend fun insertAndGetNewConversationItem(title: String, imageUri: String?): ConversationItem {
        val newChatId = getLatestLocalChatId()
        val conversationEntity = ConversationEntity(newChatId, title, imageUri, null, 0)
        chatListRepository.insertConversationEntity(conversationEntity)

        return conversationEntity.toConversationItem(null, null)
    }

    override fun getConversationItems(): CommonFlow<List<ConversationItem>> {
        return chatListRepository.getConversationEntities().map {
            it.map {
                it.toConversationItem(null, null)
            }
        }.toCommonFlow()
    }


    override suspend fun insertChatListItem(conversationEntity: ConversationEntity) {
        chatListRepository.insertConversationEntity(conversationEntity)
    }

    override suspend fun updateChatListItem(conversationEntity: ConversationEntity) {
        chatListRepository.updateChatListItem(conversationEntity)
    }

    override suspend fun updateChatListItemTitle(title: String, chatId: Long) {
        chatListRepository.updateChatListItemTitle(title, chatId)
    }

    override suspend fun updateChatListItemLastMessageId(lastMessageId: Long, chatId: Long) {
        chatListRepository.updateChatListItemLastMessageId(lastMessageId, chatId)
    }

    override suspend fun deleteChatListItem(chatId:Long) {
        chatListRepository.deleteChatListItem(chatId)
    }

    override suspend fun deleteMessagesByChatId(chatId: Long) {
        messageRepository.deleteMessageByChatId(chatId)
    }
}