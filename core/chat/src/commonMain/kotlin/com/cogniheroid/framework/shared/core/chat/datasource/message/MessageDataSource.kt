package com.cogniheroid.framework.shared.core.chat.datasource.message

import com.cogniheroid.framework.shared.core.chat.ChatDomainCore
import com.cogniheroid.framework.shared.core.chat.data.entities.MessagesEntity
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {

    companion object{
        fun getInstance(): MessageDataSource {
            return ChatDomainCore.instance.messageDataSource
        }
    }

    suspend fun getLastLocalMessageId(): Long

    fun getMessages(chatId: Long): Flow<List<MessagesEntity>>

    fun getJoinedMessageEntities(chatId: Long): Flow<List<MessageWithSender>>

    suspend fun insertMessage(messagesEntity: MessagesEntity)

    suspend fun updateMessage(messagesEntity: MessagesEntity)

    suspend fun deleteMessage(messageId: Long)

    suspend fun deleteMessageByChatId(chatId:Long)
}
