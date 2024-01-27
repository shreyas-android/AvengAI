package com.cogniheroid.framework.shared.core.chat.repository.message

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.MessagesEntity
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow

interface MessageRepository{

    companion object{

            fun getInstance(): MessageRepository {
                return ChatDomainCore.instance.messageRepository
            }
    }

   suspend fun getLastLocalMessageId(): Long

   fun getMessagesEntities(chatId: Long): CommonFlow<List<MessagesEntity>>

    suspend fun insertMessage(messagesEntity: MessagesEntity)

    suspend  fun updateMessage(messagesEntity: MessagesEntity)

    suspend fun deleteMessage(messageId: Long)

    suspend fun deleteMessageByChatId(chatId:Long)
}