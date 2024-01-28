package com.cogniheroid.framework.shared.core.chat.repository.message

import com.cogniheroid.framework.shared.core.chat.data.entities.MessagesEntity
import com.cogniheroid.framework.shared.core.chat.data.model.MessageWithSender
import com.cogniheroid.framework.shared.core.chat.datasource.message.MessageDataSource
import com.cogniheroid.framework.shared.core.chat.flow.CommonFlow
import com.cogniheroid.framework.shared.core.chat.flow.toCommonFlow

class MessageRepositoryImpl(private val messageDataSource: MessageDataSource):MessageRepository {


    override suspend fun getLastLocalMessageId(): Long {
        return messageDataSource.getLastLocalMessageId()
    }

    override fun getMessagesEntities(chatId: Long): CommonFlow<List<MessagesEntity>> {
        return messageDataSource.getMessages(chatId).toCommonFlow()
    }

    override fun getJoinedMessageEntities(chatId: Long): CommonFlow<List<MessageWithSender>> {
        return messageDataSource.getJoinedMessageEntities(chatId).toCommonFlow()
    }

    override suspend fun insertMessage(messagesEntity: MessagesEntity) {
        messageDataSource.insertMessage(messagesEntity)
    }

    override suspend fun updateMessage(messagesEntity: MessagesEntity) {
        messageDataSource.updateMessage(messagesEntity)
    }

    override suspend fun deleteMessage(messageId: Long) {
        messageDataSource.deleteMessage(messageId)
    }

    override suspend fun deleteMessageByChatId(chatId: Long) {
        messageDataSource.deleteMessageByChatId(chatId)
    }
}